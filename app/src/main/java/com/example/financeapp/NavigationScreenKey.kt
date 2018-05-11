package com.example.financeapp



class NavigationScreenKey {
    companion object {
        // Special arguments
        const val EXTRA_NEXT_FRAGMENT = "Navigation.NextFragment"
        const val EXTRA_NEXT_FRAGMENT_ARGS = "Navigation.NextFragment.Args"

        // Navigation
        const val MAIN_FRAGMENT = "main_fragment"
        const val SECOND_FRAGMENT = "second_fragment"

        // ACTIVITY
        val NAVIGATION_ACTIVITY = NavigationActivity::class.java

        val GRAPH = mapOf(
                NAVIGATION_ACTIVITY to listOf(MAIN_FRAGMENT, SECOND_FRAGMENT)
        )
    }
}