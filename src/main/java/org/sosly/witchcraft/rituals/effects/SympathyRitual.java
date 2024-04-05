package org.sosly.witchcraft.rituals.effects;

import com.mna.api.capabilities.IPlayerProgression;
import com.mna.api.rituals.IRitualContext;
import com.mna.api.rituals.RitualEffect;
import com.mna.api.spells.ICanContainSpell;
import com.mna.api.spells.base.IModifiedSpellPart;
import com.mna.api.spells.base.ISpellDefinition;
import com.mna.api.spells.parts.Shape;
import com.mna.api.spells.parts.SpellEffect;
import com.mna.api.spells.targeting.SpellContext;
import com.mna.api.spells.targeting.SpellSource;
import com.mna.api.spells.targeting.SpellTarget;
import com.mna.api.tools.MATags;
import com.mna.capabilities.playerdata.progression.PlayerProgression;
import com.mna.capabilities.playerdata.progression.PlayerProgressionProvider;
import com.mna.items.filters.SpellItemFilter;
import com.mna.spells.SpellsInit;
import com.mna.tools.StructureUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sosly.witchcraft.Config;
import org.sosly.witchcraft.Witchcraft;
import org.sosly.witchcraft.factions.FactionRegistry;
import org.sosly.witchcraft.utils.SympathyHelper;

public class SympathyRitual extends RitualEffect {
    private final Item BOUND_POPPET_ITEM;
    public static final Logger LOGGER = LogManager.getLogger(SympathyRitual.class);

    public SympathyRitual(ResourceLocation ritualName) {
        super(ritualName);
        BOUND_POPPET_ITEM = ForgeRegistries.ITEMS.getValue(new ResourceLocation(Witchcraft.MOD_ID, "bound_poppet"));
    }

    @Override
    public Component canRitualStart(IRitualContext ctx) {
        Player player = ctx.getCaster();
        if (player == null) {
            return Component.translatable("rituals.error.no_caster");
        }

        IPlayerProgression p = ctx.getCaster().getCapability(PlayerProgressionProvider.PROGRESSION).orElse(new PlayerProgression());
        if (p.getTier() < 3) {
            return Component.translatable("rituals.error.tier_required", 3);
        }

        return null;
    }

    @Override
    public boolean applyStartCheckInCreative() {
        return true;
    }

    @Override
    protected boolean applyRitualEffect(IRitualContext ctx) {
        Player player = ctx.getCaster();
        if (player == null) {
            return false;
        }

        IPlayerProgression p = player.getCapability(PlayerProgressionProvider.PROGRESSION).orElse(new PlayerProgression());
        if (p.getTier() < 3 || !FactionRegistry.isWitch(p)) {
            player.sendSystemMessage(Component.translatable("rituals.sympathy.not_a_witch"));
            return false;
        }

        ItemStack spellItem = getSpellItem(ctx);
        ItemStack boundPoppet = getBoundPoppet(ctx);
        if (spellItem.isEmpty() || boundPoppet.isEmpty()) {
            player.sendSystemMessage(Component.translatable("rituals.error.no_target"));
            return false;
        }

        ServerLevel level = (ServerLevel) ctx.getLevel();
        if (level == null) {
            return false;
        }

        Entity target = SympathyHelper.getBoundEntity(boundPoppet, level);
        if (target == null) {
            player.sendSystemMessage(Component.translatable("rituals.error.no_target"));
            return false;
        }

        if (Config.bossesBlockSympathy && isInBossArena(level, target)) {
            player.sendSystemMessage(Component.translatable("rituals.sympathy.target_protected"));
            return false;
        }

        if (Config.bossesImmuneToSympathy && SympathyHelper.isBoss(target)) {
            player.sendSystemMessage(Component.translatable("rituals.sympathy.target_protected"));
            return false;
        }

        ICanContainSpell spellItemCapability = (ICanContainSpell) spellItem.getItem();
        ISpellDefinition spell = spellItemCapability.getSpell(spellItem, player);
        if (spell.getShape() != null && isSelfSpell(spell.getShape().getPart())) {
            player.sendSystemMessage(Component.translatable("rituals.sympathy.no_self_spells"));
            return false;
        }

        IModifiedSpellPart<Shape> spellShape = spell.getShape();
        SpellSource spellSource = new SpellSource(player, InteractionHand.MAIN_HAND);
        SpellTarget spellTarget = new SpellTarget(target);
        SpellContext spellContext = new SpellContext(level, spell);

        spell.iterateComponents(component ->  {
            LOGGER.info("{} cast {} on {}.", player.getName().getString(), component.getPart().getRegistryName(),
                    target.getName().getString());
            affectTarget(level, spellShape, spellSource, spellTarget, component, spellContext);
        });

        return true;
    }

    @Override
    protected int getApplicationTicks(IRitualContext iRitualContext) {
        return 0;
    }

    private ItemStack getBoundPoppet(IRitualContext ctx) {
        return ctx.getCollectedReagents().stream()
                .filter(i -> i.getItem() == BOUND_POPPET_ITEM)
                .findFirst()
                .orElse(ItemStack.EMPTY);
    }

    private ItemStack getSpellItem(IRitualContext ctx) {
        SpellItemFilter filter = new SpellItemFilter();
        return ctx.getCollectedReagents().stream()
                .filter(filter::IsValidItem)
                .findFirst()
                .orElse(ItemStack.EMPTY);
    }

    private boolean isInBossArena(ServerLevel level, Entity target) {
        return StructureUtils.isPointInAnyStructure(level, target.blockPosition(), MATags.Structures.BOSS_ARENAS);
    }

    private boolean isSelfSpell(Shape part) {
        return part.equals(SpellsInit.SELF) || part.equals(SpellsInit.BOUND_AXE) || part.equals(SpellsInit.BOUND_SWORD)
                || part.equals(SpellsInit.BOUND_BOW) || part.equals(SpellsInit.BOUND_SHIELD);
    }

    private void affectTarget(Level level, IModifiedSpellPart<Shape> shape, SpellSource source, SpellTarget target, IModifiedSpellPart<SpellEffect> effect, SpellContext ctx) {
        effect.getPart().ApplyEffect(source, target, effect, ctx);
    }
}
