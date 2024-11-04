package com.facebook.transitions

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry

const val ANIMATION_DURATION = 350
const val PARALLAX_EFFECT_OFFSET = -400

fun AnimatedContentTransitionScope<NavBackStackEntry>.present() = slideIntoContainer(
    AnimatedContentTransitionScope.SlideDirection.Up, tween(ANIMATION_DURATION)
)

fun AnimatedContentTransitionScope<NavBackStackEntry>.push() = slideIntoContainer(
    AnimatedContentTransitionScope.SlideDirection.Left,
    animationSpec = tween(ANIMATION_DURATION)
)

fun AnimatedContentTransitionScope<NavBackStackEntry>.slideFromParallaxLeft() = slideIntoContainer(
    AnimatedContentTransitionScope.SlideDirection.Right,
    tween(ANIMATION_DURATION),
    { PARALLAX_EFFECT_OFFSET }
)

fun AnimatedContentTransitionScope<NavBackStackEntry>.dismiss() = slideOutOfContainer(
    AnimatedContentTransitionScope.SlideDirection.Down, tween(ANIMATION_DURATION)
)

fun AnimatedContentTransitionScope<NavBackStackEntry>.slideToParallaxLeft() = slideOutOfContainer(
    AnimatedContentTransitionScope.SlideDirection.Left,
    tween(ANIMATION_DURATION),
    { PARALLAX_EFFECT_OFFSET }
)

fun AnimatedContentTransitionScope<NavBackStackEntry>.slideToRight() = slideOutOfContainer(
    AnimatedContentTransitionScope.SlideDirection.Right,
    tween(ANIMATION_DURATION),
)

fun AnimatedContentTransitionScope<NavBackStackEntry>.stayStill() = slideOutOfContainer(
    AnimatedContentTransitionScope.SlideDirection.Right,
    tween(ANIMATION_DURATION),
    { 1 }
)

fun AnimatedContentTransitionScope<NavBackStackEntry>.stayStillEnter() = slideIntoContainer(
    AnimatedContentTransitionScope.SlideDirection.Left,
    tween(ANIMATION_DURATION),
    { 1 }
)
