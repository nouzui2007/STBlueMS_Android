/*
 * Copyright (c) 2017  STMicroelectronics – All rights reserved
 * The STMicroelectronics corporate logo is a trademark of STMicroelectronics
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * - Redistributions of source code must retain the above copyright notice, this list of conditions
 *   and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright notice, this list of
 *   conditions and the following disclaimer in the documentation and/or other materials provided
 *   with the distribution.
 *
 * - Neither the name nor trademarks of STMicroelectronics International N.V. nor any other
 *   STMicroelectronics company nor the names of its contributors may be used to endorse or
 *   promote products derived from this software without specific prior written permission.
 *
 * - All of the icons, pictures, logos and other images that are provided with the source code
 *   in a directory whose title begins with st_images may only be used for internal purposes and
 *   shall not be redistributed to any third party or modified in any way.
 *
 * - Any redistributions in binary form shall not include the capability to display any of the
 *   icons, pictures, logos and other images that are provided with the source code in a directory
 *   whose title begins with st_images.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
 * AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER
 * OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY
 * OF SUCH DAMAGE.
 */

package com.st.BlueMS;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.st.BlueMS.demos.AccEvent.AccEventFragment;
import com.st.BlueMS.demos.ActivityRecognitionFragment;
import com.st.BlueMS.demos.Audio.Beamforming.BeamformingFragment;
import com.st.BlueMS.demos.Audio.BlueVoice.BlueVoiceFragment;
import com.st.BlueMS.demos.Audio.DirOfArrival.SourceLocFragment;
import com.st.BlueMS.demos.CarryPositionFragment;
import com.st.BlueMS.demos.Cloud.CloudLogFragment;
import com.st.BlueMS.demos.EnvironmentalSensorsFragment;
import com.st.BlueMS.demos.HearRateFragment;
import com.st.BlueMS.demos.MemsGestureRecognitionFragment;
import com.st.BlueMS.demos.MotionIntensityFragment;
import com.st.BlueMS.demos.NodeStatus.NodeStatusFragment;
import com.st.BlueMS.demos.AccPlotFeatureFragment;
import com.st.BlueMS.demos.PedometerFragment;
import com.st.BlueMS.demos.PlotFeatureFragment;
import com.st.BlueMS.demos.ProximityGestureRecognitionFragment;
import com.st.BlueMS.demos.SwitchFragment;
import com.st.BlueMS.demos.memsSensorFusion.CompassFragment;
import com.st.BlueMS.demos.memsSensorFusion.MemsSensorFusionFragment;
import com.st.BlueSTSDK.Node;
import com.st.BlueSTSDK.gui.demos.DemoFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Activity that display all the demo available for the node
 */
public class AccDemosActivity extends com.st.BlueSTSDK.gui.DemosActivity {

    /**
     * ノードリストを渡すテスト
     * by Makiuchi
     * @param c
     * @param nodes
     * @param resetCache
     * @return
     */
    public static Intent getStartIntent(Context c, List<Node> nodes, boolean resetCache) {
        Intent i = new Intent(c, AccDemosActivity.class);
        setIntentParameters(i,nodes,resetCache);
        return i;
    }//getStartIntent

    /**
     * List of all the class that extend DemoFragment class, if the board match the requirement
     * for the demo it will displayed
     */
    @SuppressWarnings("unchecked")
    private final static Class<? extends DemoFragment> ALL_DEMOS[] = new Class[]{

            AccPlotFeatureFragment.class,
    };

    @SuppressWarnings("unchecked")
    @Override
    protected Class<? extends DemoFragment>[] getAllDemos() {

        if(getNode().getType()== Node.Type.NUCLEO || getNode().getType()== Node.Type.BLUE_COIN)
            return ALL_DEMOS;
        else{
            ArrayList<Class<? extends DemoFragment>> demoList = new ArrayList<>(Arrays.asList(ALL_DEMOS));
            demoList.remove(BeamformingFragment.class);
            return demoList.toArray(new Class[demoList.size()]);
        }
    }

    @Override
    protected boolean enableLicenseManager() {
        return true;
    }

    @Override
    protected boolean enableFwUploading() {
        return true;
    }

}
