package thedarkcolour.hardcoredungeons.client.model.armor;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

/**
 * Borrowed from BananaBank.
 * @author thedarkcolour
 */
public class ArmorModel extends HumanoidModel<LivingEntity> {
    public ArmorModel(ModelPart root, EquipmentSlot slot) {
        super(root);

        if (slot != EquipmentSlot.CHEST) {
            body.visible = false;
            rightArm.visible = false;
            leftArm.visible = false;
        }
        if (slot != EquipmentSlot.HEAD) {
            head.visible = false;
        }
    }
}