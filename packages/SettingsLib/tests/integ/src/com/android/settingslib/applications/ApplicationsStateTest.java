/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settingslib.applications;

import static com.google.common.truth.Truth.assertThat;

import static org.mockito.Mockito.mock;

import android.content.pm.ApplicationInfo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ApplicationsStateTest {
    private ApplicationsState.AppFilter mFilter;
    private ApplicationsState.AppEntry mEntry;

    @Before
    public void setUp() {
        mFilter = ApplicationsState.FILTER_GAMES;
        mEntry = mock(ApplicationsState.AppEntry.class);
        mEntry.info = mock(ApplicationInfo.class);
    }

    @Test
    public void testGamesFilterAcceptsGameDeprecated() {
        mEntry.info.flags = ApplicationInfo.FLAG_IS_GAME;

        assertThat(mFilter.filterApp(mEntry)).isTrue();
    }

    @Test
    public void testGameFilterAcceptsCategorizedGame() {
        mEntry.info.category = ApplicationInfo.CATEGORY_GAME;

        assertThat(mFilter.filterApp(mEntry)).isTrue();
    }

    @Test
    public void testGameFilterAcceptsCategorizedGameAndDeprecatedIsGame() {
        mEntry.info.flags = ApplicationInfo.FLAG_IS_GAME;
        mEntry.info.category = ApplicationInfo.CATEGORY_GAME;

        assertThat(mFilter.filterApp(mEntry)).isTrue();
    }

    @Test
    public void testGamesFilterRejectsNotGame() {
        mEntry.info.category = ApplicationInfo.CATEGORY_UNDEFINED;

        assertThat(mFilter.filterApp(mEntry)).isFalse();
    }
}