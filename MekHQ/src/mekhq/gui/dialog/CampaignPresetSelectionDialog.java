/*
 * Copyright (c) 2021 - The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MekHQ.
 *
 * MekHQ is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MekHQ is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MekHQ. If not, see <http://www.gnu.org/licenses/>.
 */
package mekhq.gui.dialog;

import megamek.common.annotations.Nullable;
import mekhq.campaign.GamePreset;
import mekhq.gui.view.CampaignPresetSelectionPanel;

import javax.swing.*;
import java.awt.*;

public class CampaignPresetSelectionDialog extends BaseDialog {
    //region Variable Declarations
    private CampaignPresetSelectionPanel presetSelectionPanel;
    //endregion Variable Declarations

    //region Constructors
    protected CampaignPresetSelectionDialog(final JFrame parent) {
        super(parent, true, "CampaignPresetSelectionDialog.title");
        initialize("CampaignPresetSelectionDialog");
    }
    //endregion Constructors

    //region Getters/Setters
    public CampaignPresetSelectionPanel getPresetSelectionPanel() {
        return presetSelectionPanel;
    }

    public void setPresetSelectionPanel(final CampaignPresetSelectionPanel presetSelectionPanel) {
        this.presetSelectionPanel = presetSelectionPanel;
    }

    /**
     * @return the selected preset, or null if the dialog was cancelled or no preset was selected
     */
    public @Nullable GamePreset getSelectedPreset() {
        return getResult().isConfirmed() ? getPresetSelectionPanel().getSelectedPreset() : null;
    }
    //endregion Getters/Setters

    //region Initialization
    @Override
    protected Container createCenterPane() {
        setPresetSelectionPanel(new CampaignPresetSelectionPanel(GamePreset.getGamePresetsIn()));
        return getPresetSelectionPanel();
    }
    //endregion Initialization

    @Override
    protected void okAction() {

    }

    @Override
    protected void cancelAction() {

    }

    @Override
    public void setVisible(final boolean visible) {
        // Only show if there are presets to select from
        super.setVisible(visible && (getPresetSelectionPanel().getPresets().getModel().getSize() > 0));
    }
}
