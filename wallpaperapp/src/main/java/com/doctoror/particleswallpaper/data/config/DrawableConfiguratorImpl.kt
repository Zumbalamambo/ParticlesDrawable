/*
 * Copyright (C) 2017 Yaroslav Mytkalyk
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
package com.doctoror.particleswallpaper.data.config

import com.doctoror.particlesdrawable.ParticlesDrawable
import com.doctoror.particleswallpaper.data.mapper.DotRadiusMapper
import com.doctoror.particleswallpaper.domain.config.DrawableConfigurator
import com.doctoror.particleswallpaper.domain.repository.SettingsRepository
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Yaroslav Mytkalyk on 29.05.17.
 *
 * Not thread safe!
 */
class DrawableConfiguratorImpl : DrawableConfigurator {

    var disposables : CompositeDisposable? = null

    override fun subscribe(drawable: ParticlesDrawable, settings: SettingsRepository) {
        disposables?.dispose()
        disposables = CompositeDisposable()

        disposables!!.add(settings.getColor().subscribe({ c ->
            drawable.setDotColor(c)
            drawable.setLineColor(c)
        }))

        disposables!!.add(settings.getNumDots().subscribe({ v ->
            drawable.setNumDots(v)
            drawable.makeBrandNewFrame()
        }))

        disposables!!.add(settings.getDotScale().subscribe({ v ->
            val radiusRange = DotRadiusMapper.transform(v)
            drawable.setDotRadiusRange(radiusRange.first, radiusRange.second)
            drawable.makeBrandNewFrame()
        }))

        disposables!!.add(settings.getLineScale().subscribe({ v ->
            drawable.setLineThickness(v)
            drawable.makeBrandNewFrame()
        }))

        disposables!!.add(settings.getLineDistance().subscribe({ v ->
            drawable.setLineDistance(v)
        }))

        disposables!!.add(settings.getStepMultiplier().subscribe({ v ->
            drawable.setStepMultiplier(v)
        }))

        disposables!!.add(settings.getFrameDelay().subscribe({ v ->
            drawable.setFrameDelay(v)
        }))
    }

    override fun dispose() {
        disposables?.dispose()
        disposables = null
    }
}