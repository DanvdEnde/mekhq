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
package mekhq.campaign.personnel.ranks;

import megamek.common.annotations.Nullable;
import mekhq.MekHQ;
import mekhq.campaign.Campaign;
import mekhq.campaign.personnel.Person;
import mekhq.campaign.personnel.enums.Profession;

import javax.swing.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class RankValidator {
    //region Constructors
    public RankValidator() {

    }
    //endregion Constructors

    public boolean validate(final @Nullable RankSystem rankSystem, final boolean checkCode) {
        return validate(null, rankSystem, checkCode);
    }

    public boolean validate(final @Nullable DefaultComboBoxModel<RankSystem> rankSystemsModel,
                             final @Nullable RankSystem rankSystem, final boolean checkCode) {
        // Null is never a valid rank system, but this catches some default returns whose errors are
        // caught during the loading process. This MUST be the first check and CANNOT be removed.
        if (rankSystem == null) {
            return false;
        }

        // If the code is a duplicate, we've got a duplicate key error
        if (checkCode) {
            boolean duplicateKey = false;
            if (rankSystemsModel == null) {
                duplicateKey = Ranks.getRankSystems().containsKey(rankSystem.getCode());
            } else {
                for (int i = 0; i < rankSystemsModel.getSize(); i++) {
                    if (rankSystem.equals(rankSystemsModel.getElementAt(i))) {
                        duplicateKey = true;
                        break;
                    }
                }
            }

            if (duplicateKey) {
                if (rankSystem.getType().isUserData()) {
                    MekHQ.getLogger().error("Duplicate Rank System Code: " + rankSystem.getCode()
                            + ". Current " + Ranks.getRankSystems().get(rankSystem.getCode()).toString()
                            + " is duplicated by userData Rank System " + rankSystem.toString());
                } else {
                    MekHQ.getLogger().error("Duplicate Rank System Code: " + rankSystem.getCode()
                            + ". Current " + Ranks.getRankSystems().get(rankSystem.getCode()).toString()
                            + " is duplicated by " + rankSystem.toString());
                }
                return false;
            }
        }

        // Default System Validation has passed successfully
        if (rankSystem.getType().isDefault()) {
            return true;
        }

        // Now for the more computationally intensive processing, the rank validation
        // First, let's check the size, as we currently require a size equal to the total number of
        // rank tiers
        if (rankSystem.getRanks().size() != Rank.RC_NUM) {
            MekHQ.getLogger().error(String.format("Illegal number of ranks of %d when %d is required",
                    rankSystem.getRanks().size(), Rank.RC_NUM));
            return false;
        }

        // Index 0 needs to be checked individually for empty ranks, as that is a no-go.
        // Additionally, we need to setup the default professions for later redirect testing
        final Rank initialRank = rankSystem.getRank(0);
        final Profession[] professions = Profession.values();
        final Set<Profession> defaultProfessions = new HashSet<>(); // Professions with legal level 1 names
        for (final Profession profession : professions) {
            if (initialRank.isEmpty(profession)) {
                MekHQ.getLogger().error("Illegal Rank index 0 empty profession of " + profession + " for " + rankSystem);
                return false;
            } else if (!initialRank.indicatesAlternativeSystem(profession)) {
                defaultProfessions.add(profession);
            }
        }

        // We do require a single default profession
        if (defaultProfessions.isEmpty()) {
            MekHQ.getLogger().error("You cannot have Rank Index 0 all indicate alternative professions for " + rankSystem);
        }

        // Now, we need to check each profession
        for (final Profession profession : professions) {
            // Default professions do not indicate an alternative and cannot be empty, so we can skip
            // some processing for them
            if (!defaultProfessions.contains(profession)) {
                // Check the initial rank to ensure it doesn't include an infinite loop for this profession
                if (!validateRankAlternatives(initialRank, profession, professions.length, 0)) {
                    return false;
                }

                // Empty professions at this point can be skipped, as they are valid if their initial
                // rank isn't an infinite loop
                if (profession.isEmptyProfession(rankSystem)) {
                    continue;
                }
            }

            // Now we need to validate the rest of this system's ranks for this profession
            for (final Rank rank : rankSystem.getRanks()) {
                if (rank.indicatesAlternativeSystem(profession)
                        && !validateRankAlternatives(rank, profession, professions.length, 0)) {
                    return false;
                }
            }
        }

        // Validation has passed successfully
        return true;
    }

    private boolean validateRankAlternatives(final Rank rank, final Profession profession,
                                             final int maxRecursions, final int recursion) {
        if (recursion > maxRecursions) {
            MekHQ.getLogger().error("");
            return false;
        } else if (rank.isEmpty(profession)) {
            MekHQ.getLogger().error("");
            return false;
        } else if (rank.indicatesAlternativeSystem(profession)) {
            return validateRankAlternatives(rank, profession.getAlternateProfession(rank),
                    maxRecursions, recursion + 1);
        } else {
            return true;
        }
    }

    public void checkAssignedRankSystems(final Campaign campaign) {
        // First, we need to ensure the campaign's rank system was refreshed. This can be done by
        // checking if the system is a campaign custom
        if (!campaign.getRankSystem().getType().isCampaign()) {
            // This ensures it properly changes, with fallback properly handled
            campaign.setRankSystemDirect(Ranks.getRankSystemFromCode(campaign.getRankSystem().getCode()));
        }

        // Then, we need to fix any old rank system assignments for personnel
        campaign.getPersonnel().parallelStream().filter(person -> !person.getRankSystem().getType().isCampaign())
                .forEach(person -> person.setRankSystem(this,
                        Ranks.getRankSystemFromCode(person.getRankSystem().getCode())));
    }

    public void changeCampaignRankSystem(final RankSystem oldRankSystem,
                                         final RankSystem newRankSystem,
                                         final Collection<Person> personnel) {
        // We need to swap over the previous rank system to the current one
        personnel.parallelStream().filter(person -> person.getRankSystem().equals(oldRankSystem))
                .forEach(person -> person.setRankSystem(this, newRankSystem));

        // Then, we need to check the ranks for the personnel
        checkPersonnelRanks(personnel);
    }

    public void checkPersonnelRanks(final Collection<Person> personnel) {
        personnel.parallelStream().forEach(this::checkPersonRank);
    }

    public void checkPersonRank(final Person person) {
        final RankSystem rankSystem = person.getRankSystem();
        final Profession profession = Profession.getProfessionFromPersonnelRole(person.getPrimaryRole())
                .getBaseProfession(rankSystem);
        if (person.getRank().isEmpty(profession)) {
            for (int i = person.getRankNumeric() - 1; i == 0; i--) {
                if (!rankSystem.getRanks().get(i).isEmpty(profession)) {
                    person.setRank(i);
                }
            }
        }
    }
}
