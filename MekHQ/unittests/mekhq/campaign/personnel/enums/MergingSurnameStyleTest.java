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
package mekhq.campaign.personnel.enums;

import megamek.common.util.EncodeControl;
import mekhq.MekHQ;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MergingSurnameStyleTest {
    //region Variable Declarations
    private static final MergingSurnameStyle[] styles = MergingSurnameStyle.values();

    private final transient ResourceBundle resources = ResourceBundle.getBundle("mekhq.resources.Personnel",
            MekHQ.getMHQOptions().getLocale(), new EncodeControl());
    //endregion Variable Declarations

    //region Boolean Comparison Methods
    @Test
    public void testIsNoChange() {
        for (final MergingSurnameStyle mergingSurnameStyle : styles) {
            if (mergingSurnameStyle == MergingSurnameStyle.NO_CHANGE) {
                assertTrue(mergingSurnameStyle.isNoChange());
            } else {
                assertFalse(mergingSurnameStyle.isNoChange());
            }
        }
    }

    @Test
    public void testIsYours() {
        for (final MergingSurnameStyle mergingSurnameStyle : styles) {
            if (mergingSurnameStyle == MergingSurnameStyle.YOURS) {
                assertTrue(mergingSurnameStyle.isYours());
            } else {
                assertFalse(mergingSurnameStyle.isYours());
            }
        }
    }

    @Test
    public void testIsSpouse() {
        for (final MergingSurnameStyle mergingSurnameStyle : styles) {
            if (mergingSurnameStyle == MergingSurnameStyle.SPOUSE) {
                assertTrue(mergingSurnameStyle.isSpouse());
            } else {
                assertFalse(mergingSurnameStyle.isSpouse());
            }
        }
    }

    @Test
    public void testIsSpaceYours() {
        for (final MergingSurnameStyle mergingSurnameStyle : styles) {
            if (mergingSurnameStyle == MergingSurnameStyle.SPACE_YOURS) {
                assertTrue(mergingSurnameStyle.isSpaceYours());
            } else {
                assertFalse(mergingSurnameStyle.isSpaceYours());
            }
        }
    }

    @Test
    public void testIsBothSpaceYours() {
        for (final MergingSurnameStyle mergingSurnameStyle : styles) {
            if (mergingSurnameStyle == MergingSurnameStyle.BOTH_SPACE_YOURS) {
                assertTrue(mergingSurnameStyle.isBothSpaceYours());
            } else {
                assertFalse(mergingSurnameStyle.isBothSpaceYours());
            }
        }
    }

    @Test
    public void testIsHypYours() {
        for (final MergingSurnameStyle mergingSurnameStyle : styles) {
            if (mergingSurnameStyle == MergingSurnameStyle.HYPHEN_YOURS) {
                assertTrue(mergingSurnameStyle.isHyphenYours());
            } else {
                assertFalse(mergingSurnameStyle.isHyphenYours());
            }
        }
    }

    @Test
    public void testIsBothHypYours() {
        for (final MergingSurnameStyle mergingSurnameStyle : styles) {
            if (mergingSurnameStyle == MergingSurnameStyle.BOTH_HYPHEN_YOURS) {
                assertTrue(mergingSurnameStyle.isBothHyphenYours());
            } else {
                assertFalse(mergingSurnameStyle.isBothHyphenYours());
            }
        }
    }

    @Test
    public void testIsSpaceSpouse() {
        for (final MergingSurnameStyle mergingSurnameStyle : styles) {
            if (mergingSurnameStyle == MergingSurnameStyle.SPACE_SPOUSE) {
                assertTrue(mergingSurnameStyle.isSpaceSpouse());
            } else {
                assertFalse(mergingSurnameStyle.isSpaceSpouse());
            }
        }
    }

    @Test
    public void testIsBothSpaceSpouse() {
        for (final MergingSurnameStyle mergingSurnameStyle : styles) {
            if (mergingSurnameStyle == MergingSurnameStyle.BOTH_SPACE_SPOUSE) {
                assertTrue(mergingSurnameStyle.isBothSpaceSpouse());
            } else {
                assertFalse(mergingSurnameStyle.isBothSpaceSpouse());
            }
        }
    }

    @Test
    public void testIsHypSpouse() {
        for (final MergingSurnameStyle mergingSurnameStyle : styles) {
            if (mergingSurnameStyle == MergingSurnameStyle.HYPHEN_SPOUSE) {
                assertTrue(mergingSurnameStyle.isHyphenSpouse());
            } else {
                assertFalse(mergingSurnameStyle.isHyphenSpouse());
            }
        }
    }

    @Test
    public void testIsBothHypSpouse() {
        for (final MergingSurnameStyle mergingSurnameStyle : styles) {
            if (mergingSurnameStyle == MergingSurnameStyle.BOTH_HYPHEN_SPOUSE) {
                assertTrue(mergingSurnameStyle.isBothHyphenSpouse());
            } else {
                assertFalse(mergingSurnameStyle.isBothHyphenSpouse());
            }
        }
    }

    @Test
    public void testIsMale() {
        for (final MergingSurnameStyle mergingSurnameStyle : styles) {
            if (mergingSurnameStyle == MergingSurnameStyle.MALE) {
                assertTrue(mergingSurnameStyle.isMale());
            } else {
                assertFalse(mergingSurnameStyle.isMale());
            }
        }
    }

    @Test
    public void testIsFemale() {
        for (final MergingSurnameStyle mergingSurnameStyle : styles) {
            if (mergingSurnameStyle == MergingSurnameStyle.FEMALE) {
                assertTrue(mergingSurnameStyle.isFemale());
            } else {
                assertFalse(mergingSurnameStyle.isFemale());
            }
        }
    }

    @Test
    public void testIsWeighted() {
        for (final MergingSurnameStyle mergingSurnameStyle : styles) {
            if (mergingSurnameStyle == MergingSurnameStyle.WEIGHTED) {
                assertTrue(mergingSurnameStyle.isWeighted());
            } else {
                assertFalse(mergingSurnameStyle.isWeighted());
            }
        }
    }
    //endregion Boolean Comparison Methods

    @Disabled // FIXME : Windchild : Test Missing
    @Test
    public void testApply() {

    }

    //region File I/O
    @Test
    public void testParseFromString() {
        // Normal Parsing
        assertEquals(MergingSurnameStyle.NO_CHANGE, MergingSurnameStyle.parseFromString("NO_CHANGE"));
        assertEquals(MergingSurnameStyle.BOTH_SPACE_SPOUSE, MergingSurnameStyle.parseFromString("BOTH_SPACE_SPOUSE"));

        // Legacy Parsing - Enum Renames
        assertEquals(MergingSurnameStyle.HYPHEN_YOURS, MergingSurnameStyle.parseFromString("HYP_YOURS"));
        assertEquals(MergingSurnameStyle.BOTH_HYPHEN_YOURS, MergingSurnameStyle.parseFromString("BOTH_HYP_YOURS"));
        assertEquals(MergingSurnameStyle.HYPHEN_SPOUSE, MergingSurnameStyle.parseFromString("HYP_SPOUSE"));
        assertEquals(MergingSurnameStyle.BOTH_HYPHEN_SPOUSE, MergingSurnameStyle.parseFromString("BOTH_HYP_SPOUSE"));

        // Error Case
        assertEquals(MergingSurnameStyle.FEMALE, MergingSurnameStyle.parseFromString("blah"));
    }
    //endregion File I/O

    @Test
    public void testToStringOverride() {
        assertEquals(resources.getString("MergingSurnameStyle.BOTH_SPACE_SPOUSE.text"),
                MergingSurnameStyle.BOTH_SPACE_SPOUSE.toString());
        assertEquals(resources.getString("MergingSurnameStyle.WEIGHTED.text"),
                MergingSurnameStyle.WEIGHTED.toString());
    }
}
