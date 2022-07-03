/*
 * Copyright (c) 2022 - The MegaMek Team. All Rights Reserved.
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
package mekhq.campaign.personnel.death;

import mekhq.campaign.CampaignOptions;
import mekhq.campaign.personnel.enums.TenYearAgeRange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;

/**
 * Personnel Testing Tracker:
 * 1) Death:
 *  a) AbstractRandomDeath
 * 2) Divorce:
 *  a) AbstractRandomDivorce
 * 3) Marriage:
 *  a) AbstractRandomMarriage
 * 4) Procreation:
 *  a) AbstractRandomProcreation
 * 5) Enums:
 *  a) FamilialRelationshipType
 *  b) Profession
 *  c) SplittingSurnameStyle
 *  d) MergingSurnameStyle
 */
@ExtendWith(value = MockitoExtension.class)
public class AbstractRandomDeathTest {
    @Mock
    private CampaignOptions mockOptions;

    @BeforeEach
    public void beforeEach() {
        when(mockOptions.getEnabledRandomDeathAgeGroups()).thenReturn(new HashMap<>());
        when(mockOptions.isUseRandomClanPersonnelDeath()).thenReturn(false);
        when(mockOptions.isUseRandomPrisonerDeath()).thenReturn(false);
        when(mockOptions.isUseRandomDeathSuicideCause()).thenReturn(false);

        // Test with the easiest enabled version
        when(mockOptions.getPercentageRandomDeathChance()).thenReturn(1d);
    }
}
