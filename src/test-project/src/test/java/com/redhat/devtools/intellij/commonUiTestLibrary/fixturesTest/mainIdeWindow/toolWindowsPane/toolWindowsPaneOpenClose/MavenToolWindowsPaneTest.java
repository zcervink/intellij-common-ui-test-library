/*******************************************************************************
 * Copyright (c) 2021 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 * Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package com.redhat.devtools.intellij.commonUiTestLibrary.fixturesTest.mainIdeWindow.toolWindowsPane.toolWindowsPaneOpenClose;

import com.redhat.devtools.intellij.commonUiTestLibrary.fixtures.mainIdeWindow.toolWindowsPane.ProjectExplorer;
import com.redhat.devtools.intellij.commonUiTestLibrary.fixtures.mainIdeWindow.toolWindowsPane.ToolWindowsPane;
import com.redhat.devtools.intellij.commonUiTestLibrary.fixtures.mainIdeWindow.toolWindowsPane.buildToolPane.MavenBuildToolPane;
import com.redhat.devtools.intellij.commonUiTestLibrary.utils.testExtension.ScreenshotAfterTestFailExtension;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.Duration;

import static com.redhat.devtools.intellij.commonUiTestLibrary.utils.labels.ButtonLabels.mavenStripeButtonLabel;
import static com.redhat.devtools.intellij.commonUiTestLibrary.utils.labels.ButtonLabels.projectStripeButtonLabel;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * MavenToolWindowsPane test
 *
 * @author zcervink@redhat.com
 */
@ExtendWith(ScreenshotAfterTestFailExtension.class)
class MavenToolWindowsPaneTest extends AbstractToolWindowsPaneTest {
    @BeforeAll
    public static void prepareProject() {
        createNewProject(mavenProjectName, "Maven");
        toolWindowsPane = remoteRobot.find(ToolWindowsPane.class, Duration.ofSeconds(10));
    }

    @Test
    public void mavenBuildToolPaneOpenCloseTest() {
        if (isPaneOpened(MavenBuildToolPane.class)) {
            closePane(mavenStripeButtonLabel, MavenBuildToolPane.class);
        }
        toolWindowsPane.openMavenBuildToolPane();
        assertTrue(isPaneOpened(MavenBuildToolPane.class), "The 'Maven Build Tool Pane' should be opened but is closed.");
        toolWindowsPane.closeMavenBuildToolPane();
        assertFalse(isPaneOpened(MavenBuildToolPane.class), "The 'Maven Build Tool Pane' should be closed but is opened.");
    }

    @Test
    public void projectExplorerPaneOpenCloseTest() {
        if (isPaneOpened(ProjectExplorer.class)) {
            closePane(projectStripeButtonLabel, ProjectExplorer.class);
        }
        toolWindowsPane.openProjectExplorer();
        assertTrue(isPaneOpened(ProjectExplorer.class), "The 'Project Explorer' should be opened but is closed.");
        toolWindowsPane.closeProjectExplorer();
        assertFalse(isPaneOpened(ProjectExplorer.class), "The 'Project Explorer' should be closed but is opened.");
    }
}