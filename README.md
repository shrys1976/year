# YEAR

Minimal Android widget that visualizes your year as dots.

One dot = One day\
White = Days passed\
Grey = Days remaining

No accounts.\
No tracking.\
No clutter.\
Just time.

------------------------------------------------------------------------

## Preview

A minimal black widget showing year progress using dot grid
visualization.

------------------------------------------------------------------------






------------------------------------------------------------------------

## Architecture

    app/
     ├ widget/
     │ └ YearWidgetProvider.kt
     │
     ├ util/
     │ ├ DateUtils.kt
     │ └ WidgetUpdateScheduler.kt
     │
     ├ res/
     │ ├ layout/
     │ │ ├ activity_main.xml
     │ │ └ widget_year.xml
     │ │
     │ ├ drawable/
     │ │ ├ dot_white.xml
     │ │ ├ dot_grey.xml
     │ │ └ widget_background.xml

------------------------------------------------------------------------

## Installation (Manual APK)

1.  Download latest APK from Releases\
2.  Enable "Install Unknown Apps" on Android\
3.  Install APK\
4.  Add widget from home screen

------------------------------------------------------------------------

## Building From Source

### Requirements

-   Android Studio
-   JDK 17
-   Android SDK 34+

------------------------------------------------------------------------

### Build APK

    Build → Generate Signed Bundle / APK → APK → Release

Output location:

    app/build/outputs/apk/release/

------------------------------------------------------------------------

