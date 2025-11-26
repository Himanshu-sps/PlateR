package com.plater.android.presentation.uiresources

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import com.plater.android.R
import com.plater.android.core.utils.DimensUtils
import com.plater.android.core.utils.nonScaledSp

/**
 * Typography System Documentation
 *
 * This file defines the typography system for PlateR app using Poppins font family.
 *
 * FONT SCALING BEHAVIOR:
 * ──────────────────────
 * All typography styles use FIXED sizes that IGNORE system font size settings.
 * This ensures consistent typography across all devices regardless of user's font size preference.
 * The app will maintain the same font sizes even if users change their system font size settings.
 *
 * TYPOGRAPHY HIERARCHY & USAGE GUIDE:
 *
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ DISPLAY STYLES (50sp) - Largest text, maximum impact                    │
 * ├─────────────────────────────────────────────────────────────────────────┤
 * │ • displayLarge (Bold):   Hero screens, splash screens, main app title   │
 * │ • displayMedium (Regular): Large decorative text, welcome messages     │
 * │                                                                          │
 * │ Example: "Welcome to PlateR" on onboarding screen                       │
 * └─────────────────────────────────────────────────────────────────────────┘
 *
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ HEADLINE STYLES (30sp) - Section headers, page titles                  │
 * ├─────────────────────────────────────────────────────────────────────────┤
 * │ • headlineLarge (Bold):   Screen titles, major section headers           │
 * │ • headlineMedium (Regular): Subsection titles, secondary headers        │
 * │                                                                          │
 * │ Example: "My Recipes", "Order History", "Profile Settings"              │
 * └─────────────────────────────────────────────────────────────────────────┘
 *
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ TITLE STYLES (18-20sp) - Card titles, list item headers                 │
 * ├─────────────────────────────────────────────────────────────────────────┤
 * │ • titleLarge (Bold):      Card titles, prominent list items             │
 * │ • titleMedium (Regular):  Secondary card titles, list item names        │
 * │ • titleSmall (Bold):      Subsection titles, category names             │
 * │                                                                          │
 * │ Example: Recipe card title "Spaghetti Carbonara", Menu item name         │
 * └─────────────────────────────────────────────────────────────────────────┘
 *
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ BODY STYLES (14-16sp) - Main content, descriptions, paragraphs         │
 * ├─────────────────────────────────────────────────────────────────────────┤
 * │ • bodyLarge (Bold):       Important content, emphasized descriptions     │
 * │ • bodyMedium (Regular):   Main body text, descriptions, paragraphs      │
 * │ • bodySmall (Bold):       Secondary content, short descriptions          │
 * │                                                                          │
 * │ Example: Recipe description, product details, user bio                  │
 * └─────────────────────────────────────────────────────────────────────────┘
 *
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ LABEL STYLES (11-14sp) - Buttons, captions, metadata, small text         │
 * ├─────────────────────────────────────────────────────────────────────────┤
 * │ • labelLarge (Bold):      Button text, important labels                 │
 * │ • labelMedium (Regular):  Captions, timestamps, metadata                │
 * │ • labelSmall (Regular):   Fine print, disclaimers, helper text          │
 * │                                                                          │
 * │ Example: Button labels, "Posted 2 hours ago", "Terms & Conditions"      │
 * └─────────────────────────────────────────────────────────────────────────┘
 *
 * QUICK REFERENCE:
 * ───────────────
 * • Need maximum impact?          → displayLarge/displayMedium
 * • Screen or section title?       → headlineLarge/headlineMedium
 * • Card or list item title?      → titleLarge/titleMedium/titleSmall
 * • Body text or description?    → bodyLarge/bodyMedium/bodySmall
 * • Button, label, or caption?    → labelLarge/labelMedium/labelSmall
 *
 * BOLD vs REGULAR:
 * ────────────────
 * • Bold: Use for emphasis, headings, important information
 * • Regular: Use for body text, descriptions, secondary information
 *
 * REAL-WORLD EXAMPLES:
 * ────────────────────
 * Login Screen:
 *   - "Welcome Back" → displayLarge (Bold)
 *   - "Email" label → labelMedium (Regular)
 *   - Button "Sign In" → labelLarge (Bold)
 *
 * Recipe Card:
 *   - Recipe name → titleLarge (Bold)
 *   - Description → bodyMedium (Regular)
 *   - "30 min" → labelMedium (Regular)
 *   - "View Recipe" button → labelLarge (Bold)
 *
 * Profile Screen:
 *   - "My Profile" → headlineLarge (Bold)
 *   - Section "Account Settings" → titleSmall (Bold)
 *   - Setting name → bodyMedium (Regular)
 *   - Timestamp "Last updated: ..." → labelSmall (Regular)
 */

private val googleFontProvider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

private val poppinsBold = FontFamily(
    Font(
        googleFont = GoogleFont("Poppins"),
        fontProvider = googleFontProvider,
        weight = FontWeight.Bold
    )
)

private val poppinsRegular = FontFamily(
    Font(
        googleFont = GoogleFont("Poppins"),
        fontProvider = googleFontProvider,
        weight = FontWeight.Normal
    )
)

/**
 * Creates the PlateR typography system.
 *
 * Usage in Compose:
 * ```
 * Text(
 *     text = "Hello World",
 *     style = MaterialTheme.typography.headlineLarge
 * )
 * ```
 */
@Composable
fun plateRTypography(): Typography {
    return Typography(
        // DISPLAY STYLES - 50sp: Largest text for maximum visual impact
        // Use for: Hero screens, splash screens, main app branding, welcome messages
        // Example: "Welcome to PlateR" on onboarding, app name on splash screen
        displayLarge = TextStyle(
            fontFamily = poppinsBold,
            fontWeight = FontWeight.Bold,
            fontSize = DimensUtils.dimenSp(R.dimen.size_50).nonScaledSp,
            lineHeight = DimensUtils.dimenSp(R.dimen.size_50).nonScaledSp,
        ),
        // Use for: Large decorative text, secondary hero messages
        // Example: Subtitle on welcome screen, large decorative quotes
        displayMedium = TextStyle(
            fontFamily = poppinsRegular,
            fontWeight = FontWeight.Normal,
            fontSize = DimensUtils.dimenSp(R.dimen.size_50).nonScaledSp,
            lineHeight = DimensUtils.dimenSp(R.dimen.size_50).nonScaledSp
        ),

        // HEADLINE STYLES - 30sp: Section headers and page titles
        // Use for: Screen titles, major section headers, primary navigation labels
        // Example: "My Recipes", "Order History", "Profile", "Settings"
        headlineLarge = TextStyle(
            fontFamily = poppinsBold,
            fontWeight = FontWeight.Bold,
            fontSize = DimensUtils.dimenSp(R.dimen.size_30).nonScaledSp,
            lineHeight = DimensUtils.dimenSp(R.dimen.size_30).nonScaledSp
        ),
        // Use for: Subsection titles, secondary page headers
        // Example: "Recent Orders", "Favorite Recipes", "Account Information"
        headlineMedium = TextStyle(
            fontFamily = poppinsRegular,
            fontWeight = FontWeight.Normal,
            fontSize = DimensUtils.dimenSp(R.dimen.size_30).nonScaledSp,
            lineHeight = DimensUtils.dimenSp(R.dimen.size_30).nonScaledSp
        ),

        // TITLE STYLES - 18-20sp: Card titles and list item headers
        // Use for: Card titles, prominent list items, featured content
        // Example: Recipe card title "Spaghetti Carbonara", menu item name
        titleLarge = TextStyle(
            fontFamily = poppinsBold,
            fontWeight = FontWeight.Bold,
            fontSize = DimensUtils.dimenSp(R.dimen.size_20).nonScaledSp,
            lineHeight = DimensUtils.dimenSp(R.dimen.size_20).nonScaledSp
        ),
        // Use for: Secondary card titles, list item names, product names
        // Example: Restaurant name in card, item name in list
        titleMedium = TextStyle(
            fontFamily = poppinsRegular,
            fontWeight = FontWeight.Normal,
            fontSize = DimensUtils.dimenSp(R.dimen.size_20).nonScaledSp,
            lineHeight = DimensUtils.dimenSp(R.dimen.size_20).nonScaledSp
        ),
        // Use for: Subsection titles, category names, smaller headings
        // Example: "Appetizers", "Main Course", "Desserts" in menu
        titleSmall = TextStyle(
            fontFamily = poppinsBold,
            fontWeight = FontWeight.Bold,
            fontSize = DimensUtils.dimenSp(R.dimen.size_18).nonScaledSp,
            lineHeight = DimensUtils.dimenSp(R.dimen.size_18).nonScaledSp
        ),

        // BODY STYLES - 14-16sp: Main content, descriptions, paragraphs
        // Use for: Important content that needs emphasis, key descriptions
        // Example: Important recipe instructions, highlighted product features
        bodyLarge = TextStyle(
            fontFamily = poppinsBold,
            fontWeight = FontWeight.Bold,
            fontSize = DimensUtils.dimenSp(R.dimen.size_16).nonScaledSp,
            lineHeight = DimensUtils.dimenSp(R.dimen.size_16).nonScaledSp
        ),
        // Use for: Main body text, descriptions, paragraphs, most content
        // Example: Recipe description, product details, user bio, article content
        bodyMedium = TextStyle(
            fontFamily = poppinsRegular,
            fontWeight = FontWeight.Normal,
            fontSize = DimensUtils.dimenSp(R.dimen.size_16).nonScaledSp,
            lineHeight = DimensUtils.dimenSp(R.dimen.size_16).nonScaledSp
        ),
        // Use for: Secondary content, short descriptions, less important text
        // Example: Short product description, brief notes, secondary info
        bodySmall = TextStyle(
            fontFamily = poppinsRegular,
            fontWeight = FontWeight.Normal,
            fontSize = DimensUtils.dimenSp(R.dimen.size_14).nonScaledSp,
            lineHeight = DimensUtils.dimenSp(R.dimen.size_14).nonScaledSp,
        ),

        // LABEL STYLES - 11-14sp: Buttons, captions, metadata, small text
        // Use for: Button text, important labels, call-to-action text
        // Example: "Add to Cart", "Order Now", "Sign Up" button text
        labelLarge = TextStyle(
            fontFamily = poppinsBold,
            fontWeight = FontWeight.Bold,
            fontSize = DimensUtils.dimenSp(R.dimen.size_14).nonScaledSp,
            lineHeight = DimensUtils.dimenSp(R.dimen.size_14).nonScaledSp
        ),
        // Use for: Captions, timestamps, metadata, secondary labels
        // Example: "Posted 2 hours ago", "Price: $15.99", "Rating: 4.5"
        labelMedium = TextStyle(
            fontFamily = poppinsRegular,
            fontWeight = FontWeight.Normal,
            fontSize = DimensUtils.dimenSp(R.dimen.size_11).nonScaledSp,
            lineHeight = DimensUtils.dimenSp(R.dimen.size_15).nonScaledSp
        ),
        // Use for: Fine print, disclaimers, helper text, smallest readable text
        // Example: "Terms & Conditions", "By continuing you agree...", hints
        labelSmall = TextStyle(
            fontFamily = poppinsRegular,
            fontWeight = FontWeight.Normal,
            fontSize = DimensUtils.dimenSp(R.dimen.size_9).nonScaledSp,
            lineHeight = DimensUtils.dimenSp(R.dimen.size_12).nonScaledSp
        )
    )
}
