package com.example.financeapp.ui.navigation

//import android.support.v4.app.Fragment
import android.annotation.SuppressLint
import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.financeapp.ui.authorisation.AuthorisationActivity
import com.example.financeapp.base.GoogleApiClientBaseActivity
import com.example.financeapp.R
import com.example.financeapp.ui.custom.CircleTransform
import com.example.financeapp.ui.main.FragmentMain
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import kotlinx.android.synthetic.main.activity_navigation.*
import kotlinx.android.synthetic.main.app_bar_navigation.*
import javax.inject.Inject


class NavigationActivity : GoogleApiClientBaseActivity(), /*HasFragmentInjector,*/ GoogleApiClient.OnConnectionFailedListener {

//    @Inject
//    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

//    @Inject
//    lateinit var handler: Handler

    @Inject
    lateinit var navigationActivityViewModel: NavigationActivityViewModel

    lateinit var navHeaderNavigation: View

    lateinit var nameUser: TextView
    lateinit var emailUser: TextView
    lateinit var imgHeaderBcgr: ImageView
    lateinit var imgProfile: ImageView

//    @Inject
//    lateinit var mainFragment: FragmentMain
//
//    @Inject
//    lateinit var recordsFragment: FragmentRecords

//    @Inject
//    lateinit var mGoogleApiClient: GoogleApiClient

    private val urlNavHeaderBg = "http://api.androidhive.info/images/nav-menu-header-bg.jpg"
    private val urlProfileImg = "https://lh3.googleusercontent.com/eCtE_G34M9ygdkmOpYvCag1vBARCmZwnVS6rS5t4JLzJ6QgQSBquM0nuTsCpLhYbKljoyS-txg"

    // index to identify current nav menu item
    var navItemIndex = 0

    // tags used to attach the fragments
    private val TAG_MAIN = "main"
    private val TAG_RECORDS = "records"
    private val TAG_CREDIT_CALCULATOR = "credit_calculator"
    private val TAG_REMINDERS = "reminders"
    private val TAG_EXCHANGE_RATES = "exchange_rates"
    var CURRENT_TAG = TAG_MAIN

    // toolbar titles respected to selected nav menu item
    private lateinit var activityTitles: Array<String>

    // flag to load home fragment when user presses back key
    private val shouldLoadHomeFragOnBackPress = true

    override fun onCreate(savedInstanceState: Bundle?) {

//        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        setSupportActionBar(toolbar)

//        Запись в SPref статус: Вход выполнен
        sPrefHelper.setSignInAccount(true)

        // load toolbar titles from string resources
        activityTitles = resources.getStringArray(R.array.nav_item_activity_titles)

//        navHeaderNavigation = layoutInflater.inflate(R.layout.nav_header_navigation, null);
        navHeaderNavigation = nav_view.getHeaderView(0)

        nameUser = navHeaderNavigation.findViewById(R.id.nameUser)
        emailUser = navHeaderNavigation.findViewById(R.id.emailUser)
        imgHeaderBcgr = navHeaderNavigation.findViewById(R.id.img_header_bg)
        imgProfile = navHeaderNavigation.findViewById(R.id.img_profile)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        navigationActivityViewModel.loadDataFromGoogleApiToDB(mGoogleApiClient)

        // load nav menu header data
        loadNavHeader()

        // initializing navigation menu
        setUpNavigationView()

        if (savedInstanceState == null) {
            navItemIndex = 0
            CURRENT_TAG = TAG_MAIN
            loadHomeFragment()
        }

        // Log Out
        imgProfile.setOnClickListener {

            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback { status ->
                if (status.isSuccess) {
                    goLogInScreen()
                } else {
                    Toast.makeText(this, "Log Out", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    /***
     * Load navigation menu header information
     * like background image, profile image
     * name, website, notifications action view (dot)
     */
    private fun loadNavHeader() {



//        val opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient)
//        if (opr.isDone) {
//            val result = opr.get()
//            handleSignInResult(result)
//        } else {
//            opr.setResultCallback { googleSignInResult -> handleSignInResult(googleSignInResult) }
//        }

//        // name, website
//        nameUser.text = "Ravi Tamada"
//        emailUser.text = "www.androidhive.info"
//
//        // loading header background image
//        Glide.with(this).load(urlNavHeaderBg)
//                .bitmapTransform(BlurTransformation(this, 25))
//                .crossFade()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(imgHeaderBcgr)
//
//        // Loading profile image
//        Glide.with(this).load(urlProfileImg)
//                .crossFade()
//                .thumbnail(0.5f)
//                .bitmapTransform(CircleTransform(this))
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(imgProfile)
    }

    @SuppressLint("SetTextI18n")
    private fun handleSignInResult(result: GoogleSignInResult) {
        if (result.isSuccess) {
            val account = result.signInAccount

            /*
            * nameUser = navHeaderNavigation.findViewById(R.id.nameUser)
        emailUser = navHeaderNavigation.findViewById(R.id.emailUser)
        imgHeaderBcgr = navHeaderNavigation.findViewById(R.id.img_header_bg)
        imgProfile = navHeaderNavigation.findViewById(R.id.img_profile)
            * */



            nameUser.text = account!!.displayName
            emailUser.text = account.email

            Log.d("myLog", "getFamilyName() = " + account.familyName + " \n" +
                    "getGivenName() = " + account.givenName)

            Log.d("myLog", "account.getPhotoUrl() = " + account.photoUrl)

            // loading header background image
            Glide.with(this).load(R.drawable.nav_profile_bacground)
//                        .bitmapTransform(BlurTransformation(this, 200))
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgHeaderBcgr)

            if (account.photoUrl == null){

//                imgHeaderBcgr.setImageResource(R.drawable.side_nav_bar)

//                // loading header background image
//                Glide.with(this).load(R.drawable.side_nav_bar)
//                        .crossFade()
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                        .into(imgHeaderBcgr)

                // Loading profile image
                Glide.with(this).load(R.drawable.profile_img)
                        .crossFade()
                        .thumbnail(0.5f)
                        .bitmapTransform(CircleTransform(this))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imgProfile)

            } else {

                // Loading profile image
                Glide.with(this).load(account.photoUrl)
                        .crossFade()
                        .thumbnail(0.5f)
                        .bitmapTransform(CircleTransform(this))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imgProfile)

            }


        } else {
            goLogInScreen()
        }
    }

//    private fun goLogInScreen() {
//
////        Запись в SPref статус: Выход из аккаунта
//        sPrefHelper.setSignInAccount(false)
//
//        val intent = Intent(this, AuthorisationActivity::class.java)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
//        startActivity(intent)
//    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private fun loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu()

        // set toolbar title
        setToolbarTitle()

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (supportFragmentManager.findFragmentByTag(CURRENT_TAG) != null) {
            drawer_layout.closeDrawers()

            // show or hide the fab button
            toggleFab()
            return
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        val mPendingRunnable = Runnable {
            // update the main content by replacing fragments
            val fragment = getHomeFragment()
//            fragment.arguments = intent.extras
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out)
            fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit()
//            fragmentTransaction.add(R.id.frame, fragment)
            fragmentTransaction.commitAllowingStateLoss()
        }

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            handler.post(mPendingRunnable)
        }

        // show or hide the fab button
        toggleFab()

        //Closing drawer on item click
        drawer_layout.closeDrawers()

        // refresh toolbar menu
        invalidateOptionsMenu()
    }

    private fun getHomeFragment(): Fragment {
        when (navItemIndex) {
            0 -> {
                // main
                return mainFragment
            }
            1 -> {
                // records
                return recordsFragment
            }
//            2 -> {
//
//            }
//            3 -> {
//
//            }
//
//            4 -> {
//
//            }
            else -> return FragmentMain()
        }
    }

    private fun setToolbarTitle() {
        supportActionBar!!.setTitle(activityTitles[navItemIndex])
    }

    private fun selectNavMenu() {
        nav_view.menu.getItem(navItemIndex).setChecked(true)
    }

    private fun setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        nav_view.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { menuItem ->
            // This method will trigger on item Click of navigation menu
            //Check to see which item was being clicked and perform appropriate action
            when (menuItem.itemId) {
            //Replacing the main content with ContentFragment Which is our Inbox View;
                R.id.nav_main -> {
                    navItemIndex = 0
                    CURRENT_TAG = TAG_MAIN
                }
                R.id.nav_records -> {
                    navItemIndex = 1
                    CURRENT_TAG = TAG_RECORDS
                }
                R.id.nav_credit_calculator -> {
                    navItemIndex = 2
                    CURRENT_TAG = TAG_CREDIT_CALCULATOR
                }
                R.id.nav_reminders -> {
                    navItemIndex = 3
                    CURRENT_TAG = TAG_REMINDERS
                }
                R.id.nav_exchange_rates -> {
                    navItemIndex = 4
                    CURRENT_TAG = TAG_EXCHANGE_RATES
                }
//                R.id.nav_about_us -> {
//                    // launch new intent instead of loading fragment
//                    startActivity(Intent(this@MainActivity, AboutUsActivity::class.java))
//                    drawer.closeDrawers()
//                    return@OnNavigationItemSelectedListener true
//                }
//                R.id.nav_privacy_policy -> {
//                    // launch new intent instead of loading fragment
//                    startActivity(Intent(this@MainActivity, PrivacyPolicyActivity::class.java))
//                    drawer.closeDrawers()
//                    return@OnNavigationItemSelectedListener true
//                }
                else -> {
                    navItemIndex = 0
                    CURRENT_TAG = TAG_MAIN
                }
            }

            Log.d("myLog", "navItemIndex = $navItemIndex CURRENT_TAG = $CURRENT_TAG")

            //Checking if the item is in checked state or not, if not make it in checked state
            if (menuItem.isChecked) {
                menuItem.isChecked = false
            } else {
                menuItem.isChecked = true
            }
            menuItem.isChecked = true

            loadHomeFragment()

            true
        })


        val actionBarDrawerToggle = object : ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            override fun onDrawerClosed(drawerView: View?) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView)
            }

            override fun onDrawerOpened(drawerView: View?) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView)
            }
        }

        //Setting the actionbarToggle to drawer layout
        drawer_layout.setDrawerListener(actionBarDrawerToggle)

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawers()
            return
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0
                CURRENT_TAG = TAG_MAIN
                loadHomeFragment()
                return
            }
        }

        super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    // show or hide the fab
    private fun toggleFab() {
        if (navItemIndex == 0)
            fab.show()
        else
            fab.hide()
    }

//    override fun fragmentInjector(): AndroidInjector<Fragment> = fragmentDispatchingAndroidInjector

    override fun onConnectionFailed(connectionResult: ConnectionResult) {

    }
}
