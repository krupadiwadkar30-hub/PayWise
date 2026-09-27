package com.example.emishield

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper

import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.google.gson.JsonParser

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response

import java.io.IOException
import java.util.Currency
import java.util.Locale

import java.text.SimpleDateFormat
import java.util.Date
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext

// ============================================================
// COLORS
// ============================================================

val PrimaryBlue = Color(0xFF1565C0)
val DeepBlue = Color(0xFF0D47A1)
val Orange = Color(0xFFF57C00)
val Yellow = Color(0xFFFBC02D)
val BackgroundWhite = Color(0xFFFFFFFF)
val LightBlue = Color(0xFFE3F2FD)
val DarkText = Color(0xFF263238)
val GreyText = Color(0xFF607D8B)
val SuccessGreen = Color(0xFF2E7D32)
val ErrorRed = Color(0xFFD32F2F)

const val BASE_URL = "https://frayed-bunny-ritzy.ngrok-free.dev"

// ============================================================
// LANGUAGE TRANSLATION
// ============================================================

// ============================================================
// PAYWISE - TRANSLATION SECTION
// Languages:
// English, Hindi, Marathi, Gujarati, Bengali,
// Tamil, Telugu, Chinese, Arabic, Korean,
// Japanese, German, Spanish, French
// ============================================================

fun tr(
    key: String,
    language: String
): String {

    return when (language.lowercase()) {

        "en", "english" -> englishText(key)

        "hi", "hindi" -> hindiText(key)

        "mr", "marathi" -> marathiText(key)

        "gu", "gujarati" -> gujaratiText(key)

        "bn", "bengali" -> bengaliText(key)

        "ta", "tamil" -> tamilText(key)

        "te", "telugu" -> teluguText(key)

        "zh", "chinese" -> zhText(key)

        "ar", "arabic" -> arText(key)

        "ko", "korean" -> koText(key)

        "ja", "japanese" -> japaneseText(key)

        "de", "german" -> germanText(key)

        "es", "spanish" -> spanishText(key)

        "fr", "french" -> frenchText(key)

        else -> englishText(key)
    }
}


// ============================================================
// ENGLISH
// ============================================================

fun englishText(key: String): String {

    return when (key) {

        "paywise" -> "PayWise"
        "tagline" -> "Spend Smart, Stay Wise."

        "login" -> "Login"
        "signup" -> "Create New Account"
        "create_account" -> "Create Account"
        "back_to_login" -> "Back to Login"
        "create_your_account" -> "Create your account"

        "full_name" -> "Full Name"
        "email" -> "Email ID"
        "password" -> "Password"
        "confirm_password" -> "Confirm Password"
        "mobile" -> "Mobile Number"

        "dashboard" -> "Dashboard"
        "add_salary" -> "Add Salary"
        "my_emis" -> "My EMIs"
        "add_emi" -> "Add EMI"
        "financial_summary" -> "Financial Summary"
        "profile" -> "Profile"
        "settings" -> "Settings"
        "logout" -> "Logout"

        "welcome" -> "Welcome"
        "manage_finances" -> "Manage your finances wisely."
        "loading_financial_data" -> "Loading financial data..."

        "monthly_salary" -> "Monthly Salary"
        "total_emi" -> "Total EMI"
        "remaining_salary" -> "Remaining Salary"
        "emi_ratio" -> "EMI Ratio"
        "financial_status" -> "Financial Status"
        "your_emis" -> "Your EMIs"

        "no_emi_records" -> "No EMI records found."

        "add_update_salary" -> "Add / Update Salary"
        "save_salary" -> "Save Salary"
        "enter_valid_salary" -> "Enter a valid salary."
        "salary_updated" -> "Salary updated successfully."

        "bank_lender" -> "Bank / Lender"
        "emi_amount" -> "EMI Amount"
        "due_date" -> "Due Date"
        "frequency" -> "Frequency"
        "monthly" -> "Monthly"

        "valid_emi" -> "Please enter valid EMI details."
        "emi_added" -> "EMI added successfully."

        "no_financial_data" -> "No financial data available."

        "my_profile" -> "My Profile"
        "profile_information" -> "Profile Information"
        "personal_information" -> "Personal Information"
        "not_available" -> "Not available"

        "notifications" -> "Notifications"
        "emi_due_reminders" -> "EMI Due Reminders"
        "payment_reminders" -> "Payment Reminders"
        "reminder_days" -> "Reminder Days"
        "days" -> "days"

        "financial_preferences" -> "Financial Preferences"
        "currency" -> "Currency"
        "primary_currency" -> "Primary Currency"
        "show_financial_status" -> "Show Financial Status"

        "language" -> "Language"
        "app_language" -> "App Language"

        "region" -> "Region"
        "country_region" -> "Country / Region"

        "about_paywise" -> "About PayWise"
        "version" -> "Version 1.0"

        "select_currency" -> "Select Currency"
        "search_currency" -> "Search currency or code"

        "select_country" -> "Select Country / Region"
        "search_country" -> "Search country"

        "select_language" -> "Select Language"
        "search_language" -> "Search language"

        "no_results" -> "No results found"
        "close" -> "Close"

        "please_fill_all" -> "Please fill all fields."
        "passwords_not_match" -> "Passwords do not match."
        "please_enter_login" -> "Please enter email and password."

        "profile_photo" -> "Profile Photo"
        "select_photo" -> "Select Photo"
        "edit_profile" -> "Edit Profile"
        "financial_profile" -> "Financial Profile"
        "total_active_emis" -> "Total Active EMIs"
        "total_monthly_emi" -> "Total Monthly EMI Commitment"

        "account_information" -> "Account Information"
        "account_creation_date" -> "Account Creation Date"
        "user_id" -> "User ID"

        "security" -> "Security"
        "change_password" -> "Change Password"
        "current_password" -> "Current Password"
        "new_password" -> "New Password"
        "save_changes" -> "Save Changes"
        "cancel" -> "Cancel"

        // Dashboard
        "financial_overview" -> "Financial Overview"
        "payment_reminder" -> "Payment Reminder"
        "no_valid_emi_due_date" -> "No valid EMI due date available"
        "emi_was_due_on" -> "EMI was due on {date}"
        "emi_due_today" -> "EMI is due today"
        "reminder_due_in" -> "Reminder: {lender} is due in {days} day(s)"
        "reminder_will_appear" -> "Reminder will appear {days} days before the due date"
        "due" -> "Due"
        "remind_me" -> "Remind me"
        "test_notification" -> "Test Notification"

        // Everyday Expenses
        "everyday_expenses" -> "Everyday Expenses"
        "track_daily_spending" -> "Track your daily spending, analyze it, and save more."
        "add_expense" -> "Add Expense"
        "expense_category" -> "Expense Category"
        "select_category" -> "Select Category"
        "amount" -> "Amount"
        "enter_amount" -> "Enter amount"
        "note" -> "Note"
        "enter_note" -> "Enter note"
        "note_optional" -> "Note (optional)"
        "date" -> "Date"

        "spending_limit" -> "Spending Limit"
        "spending_limits" -> "Spending Limits"
        "set_spending_limit" -> "Set Spending Limit"
        "save_expense" -> "Save Expense"
        "update_expense" -> "Update Expense"
        "delete_expense" -> "Delete Expense"
        "edit_expense" -> "Edit Expense"

        "total_expenses" -> "Total Expenses"
        "total_spent" -> "Total Spent"
        "remaining_limit" -> "Remaining Limit"
        "expense_breakdown" -> "Expense Breakdown"
        "expense_summary" -> "Expense Summary"
        "no_expenses" -> "No expenses added yet."

        "expense_added" -> "Expense added successfully."
        "expense_updated" -> "Expense updated successfully."
        "expense_deleted" -> "Expense deleted successfully."

        "invalid_amount" -> "Invalid amount."
        "amount_required" -> "Amount is required."
        "category_required" -> "Category is required."
        "category" -> "Category"
        "recent_expenses" -> "Recent Expenses"

        "current_limit" -> "Current Limit"
        "set_limit" -> "Set Limit"
        "limit_amount" -> "Limit Amount"

        "food" -> "Food"
        "travel" -> "Travel"
        "shopping" -> "Shopping"
        "bills" -> "Bills"
        "entertainment" -> "Entertainment"
        "other" -> "Other"

        "spending_limit_set" -> "Spending limit set successfully."
        "current_spending" -> "Current Spending"
        "limit" -> "Limit"
        "remaining" -> "Remaining"

        // Smart Alerts
        "smart_alert" -> "Smart Alert"
        "smart_alerts" -> "Smart Alerts"
        "spending_alerts" -> "Spending Alerts"
        "high_spending_alert" -> "High Spending Alert"
        "category_limit_alert" -> "Category Limit Alert"
        "budget_alert" -> "Budget Alert"
        "high_spending" -> "High spending"
        "approaching_limit" -> "Approaching limit"

        // Financial Health Tips
        "financial_health_tips" -> "Financial Health Tips"
        "financial_tips" -> "Financial Tips"
        "tip" -> "Tip"
        "tips" -> "Tips"
        "save_money_tip" -> "Save money by monitoring your daily expenses."
        "track_expenses_tip" -> "Track your expenses regularly."
        "avoid_unnecessary_spending_tip" -> "Avoid unnecessary spending."
        "build_emergency_fund" -> "Build an emergency fund"
        "monitor_recurring" -> "Monitor recurring expenses"
        "avoid_new_emi" -> "Avoid excessive new EMIs"
        "set_savings_goal" -> "Set a savings goal"

        // Monthly Insights
        "monthly_insights" -> "Monthly Insights"
        "monthly_overview" -> "Monthly Overview"
        "monthly_summary" -> "Monthly Summary"
        "select_month" -> "Select Month"
        "this_month" -> "This Month"
        "previous_month" -> "Previous Month"
        "next_month" -> "Next Month"
        "previous_months" -> "Previous Months"
        "current_month" -> "Current Month"
        "total_monthly_spending" -> "Total Monthly Spending"
        "monthly_expenses" -> "Monthly Expenses"
        "total_spent_this_month" -> "Total Spent This Month"
        "category_breakdown" -> "Category-wise Breakdown"
        "highest_spending_category" -> "Highest Spending Category"
        "lowest_spending_category" -> "Lowest Spending Category"
        "average_daily_spending" -> "Average Daily Spending"
        "monthly_savings" -> "Monthly Savings"
        "spending_summary" -> "Spending Summary"
        "no_monthly_insights" -> "No monthly insights available."
        "monthly_insight_message" -> "Here is an overview of your monthly spending."
        "no_data_for_month" -> "No data available for this month."
        "monthly_total" -> "Monthly Total"
        "monthly_average" -> "Monthly Average"
        "daily_average" -> "Daily Average"
        "top_category" -> "Top Category"
        "highest_expense" -> "Highest Expense"
        "lowest_expense" -> "Lowest Expense"
        "expense_trend" -> "Expense Trend"
        "spending_pattern" -> "Spending Pattern"
        "compare_months" -> "Compare Months"
        "monthly_report" -> "Monthly Report"
        "summary" -> "Summary"

        // Smart Financial Decisions
        "smart_financial_decisions" -> "Smart Financial Decisions"
        "make_smarter_decisions" -> "Make smarter decisions using your financial data."
        "what_should_i_cut" -> "What Should I Cut?"
        "what_if" -> "What If?"
        "see_reducing_spending" -> "See how reducing your spending could affect your available money."
        "current_available_money" -> "Current available money"
        "available_money" -> "Available Money"
        "available_after_suggested_cuts" -> "Available after suggested cuts"
        "smart_suggestions" -> "Smart Suggestions"
        "top_suggestions" -> "Top Suggestions"
        "suggestions" -> "Suggestions"
        "recommendations" -> "Recommendations"
        "suggested_action" -> "Suggested Action"
        "potential_savings" -> "Potential Savings"
        "potential_monthly_savings" -> "Potential Monthly Savings"
        "potential_monthly_savings_zero" -> "Potential monthly savings: {amount}"

        "add_expenses_personalized" ->
            "Add some expenses first to get personalized suggestions."

        "no_major_reduction" ->
            "Your current spending does not show any major reduction opportunity."

        "suggestions_current_spending" ->
            "Suggestions are based on your current spending patterns."

        "reduce_spending" -> "Reduce Spending"
        "reduce_category" -> "Reduce Category"
        "save_more" -> "Save More"
        "spending_habit" -> "Spending Habit"
        "financial_decision" -> "Financial Decision"

        "cut_shopping" -> "Reduce Shopping"
        "cut_food" -> "Reduce Food"
        "cut_entertainment" -> "Reduce Entertainment"
        "cut_travel" -> "Reduce Travel"
        "cut_bills" -> "Reduce Bills"
        "cut_other" -> "Reduce Other"

        "simple_actions_financial_situation" ->
            "Simple actions based on your financial situation."

        "set_aside_available_money" ->
            "Try setting aside part of your available money every month."

        "review_subscriptions" ->
            "Review subscriptions and recurring expenses regularly."

        "check_existing_emi" ->
            "Check your existing EMI commitment before adding another EMI."

        "use_savings_goal" ->
            "Use your potential monthly savings as a starting goal."

        "set_monthly_limits" ->
            "Set monthly limits for your categories."

        "spending_alerts_actual" ->
            "Spending alerts based on your actual expenses."

        "latest_entries" -> "Your latest entries."

        "cut" -> "Cut"
        "suggested" -> "Suggested"
        "spent" -> "Spent"
        "keep_it_up" -> "Keep it up!"
        "view_summary" -> "View Summary"
        "apply_suggestion" -> "Apply Suggestion"

        "date_not_set" -> "Date not set"
        "enter_valid_amount" -> "Enter a valid amount."
        "enter_valid_limit" -> "Enter a valid limit."

        "where_did_you_spend" -> "Where did you spend?"
        "expense_note" -> "Expense Note"
        "today" -> "Today"
        "yesterday" -> "Yesterday"

        "back" -> "Back"

        else -> key
    }
}


// ============================================================
// HINDI
// ============================================================

fun hindiText(key: String): String {

    return when (key) {

        "paywise" -> "PayWise"
        "tagline" -> "समझदारी से खर्च करें, समझदारी से बचत करें।"

        "login" -> "लॉगिन"
        "signup" -> "नया खाता बनाएं"
        "create_account" -> "खाता बनाएं"
        "back_to_login" -> "लॉगिन पर वापस जाएं"
        "create_your_account" -> "अपना खाता बनाएं"

        "full_name" -> "पूरा नाम"
        "email" -> "ईमेल आईडी"
        "password" -> "पासवर्ड"
        "confirm_password" -> "पासवर्ड की पुष्टि करें"
        "mobile" -> "मोबाइल नंबर"

        "dashboard" -> "डैशबोर्ड"
        "add_salary" -> "वेतन जोड़ें"
        "my_emis" -> "मेरी EMI"
        "add_emi" -> "EMI जोड़ें"
        "financial_summary" -> "वित्तीय सारांश"
        "profile" -> "प्रोफ़ाइल"
        "settings" -> "सेटिंग्स"
        "logout" -> "लॉगआउट"

        "welcome" -> "स्वागत है"
        "manage_finances" -> "अपने वित्त का समझदारी से प्रबंधन करें।"
        "loading_financial_data" -> "वित्तीय जानकारी लोड हो रही है..."

        "monthly_salary" -> "मासिक वेतन"
        "total_emi" -> "कुल EMI"
        "remaining_salary" -> "शेष वेतन"
        "emi_ratio" -> "EMI अनुपात"
        "financial_status" -> "वित्तीय स्थिति"
        "your_emis" -> "आपकी EMI"

        "no_emi_records" -> "कोई EMI रिकॉर्ड नहीं मिला।"

        "add_update_salary" -> "वेतन जोड़ें / अपडेट करें"
        "save_salary" -> "वेतन सेव करें"
        "enter_valid_salary" -> "मान्य वेतन दर्ज करें।"
        "salary_updated" -> "वेतन सफलतापूर्वक अपडेट हुआ।"

        "bank_lender" -> "बैंक / ऋणदाता"
        "emi_amount" -> "EMI राशि"
        "due_date" -> "नियत तारीख"
        "frequency" -> "आवृत्ति"
        "monthly" -> "मासिक"

        "valid_emi" -> "कृपया मान्य EMI विवरण दर्ज करें।"
        "emi_added" -> "EMI सफलतापूर्वक जोड़ी गई।"

        "no_financial_data" -> "कोई वित्तीय जानकारी उपलब्ध नहीं है।"

        "my_profile" -> "मेरी प्रोफ़ाइल"
        "profile_information" -> "प्रोफ़ाइल जानकारी"
        "personal_information" -> "व्यक्तिगत जानकारी"
        "not_available" -> "उपलब्ध नहीं"

        "notifications" -> "सूचनाएं"
        "emi_due_reminders" -> "EMI नियत तारीख अनुस्मारक"
        "payment_reminders" -> "भुगतान अनुस्मारक"
        "reminder_days" -> "अनुस्मारक दिन"
        "days" -> "दिन"

        "financial_preferences" -> "वित्तीय प्राथमिकताएं"
        "currency" -> "मुद्रा"
        "primary_currency" -> "मुख्य मुद्रा"
        "show_financial_status" -> "वित्तीय स्थिति दिखाएं"

        "language" -> "भाषा"
        "app_language" -> "ऐप भाषा"
        "region" -> "क्षेत्र"
        "country_region" -> "देश / क्षेत्र"

        "about_paywise" -> "PayWise के बारे में"
        "version" -> "संस्करण 1.0"

        "select_currency" -> "मुद्रा चुनें"
        "search_currency" -> "मुद्रा या कोड खोजें"
        "select_country" -> "देश / क्षेत्र चुनें"
        "search_country" -> "देश खोजें"
        "select_language" -> "भाषा चुनें"
        "search_language" -> "भाषा खोजें"

        "no_results" -> "कोई परिणाम नहीं मिला"
        "close" -> "बंद करें"

        "please_fill_all" -> "कृपया सभी फ़ील्ड भरें।"
        "passwords_not_match" -> "पासवर्ड मेल नहीं खाते।"
        "please_enter_login" -> "कृपया ईमेल और पासवर्ड दर्ज करें।"

        "profile_photo" -> "प्रोफ़ाइल फोटो"
        "select_photo" -> "फोटो चुनें"
        "edit_profile" -> "प्रोफ़ाइल संपादित करें"
        "financial_profile" -> "वित्तीय प्रोफ़ाइल"
        "total_active_emis" -> "कुल सक्रिय EMI"
        "total_monthly_emi" -> "कुल मासिक EMI प्रतिबद्धता"

        "account_information" -> "खाता जानकारी"
        "account_creation_date" -> "खाता बनाने की तारीख"
        "user_id" -> "यूज़र आईडी"

        "security" -> "सुरक्षा"
        "change_password" -> "पासवर्ड बदलें"
        "current_password" -> "वर्तमान पासवर्ड"
        "new_password" -> "नया पासवर्ड"
        "save_changes" -> "परिवर्तन सेव करें"
        "cancel" -> "रद्द करें"

        "financial_overview" -> "वित्तीय अवलोकन"
        "payment_reminder" -> "भुगतान अनुस्मारक"
        "no_valid_emi_due_date" -> "कोई मान्य EMI नियत तारीख उपलब्ध नहीं है"
        "emi_was_due_on" -> "EMI की नियत तारीख {date} थी"
        "emi_due_today" -> "EMI आज देय है"
        "reminder_due_in" -> "अनुस्मारक: {lender} की EMI {days} दिन में देय है"
        "reminder_will_appear" -> "अनुस्मारक नियत तारीख से {days} दिन पहले दिखाई देगा"
        "due" -> "देय"
        "remind_me" -> "मुझे याद दिलाएं"
        "test_notification" -> "टेस्ट सूचना"

        "everyday_expenses" -> "दैनिक खर्च"
        "track_daily_spending" -> "अपने दैनिक खर्च को ट्रैक करें, उसका विश्लेषण करें और अधिक बचत करें।"
        "add_expense" -> "खर्च जोड़ें"
        "expense_category" -> "खर्च की श्रेणी"
        "select_category" -> "श्रेणी चुनें"
        "amount" -> "राशि"
        "enter_amount" -> "राशि दर्ज करें"
        "note" -> "नोट"
        "enter_note" -> "नोट दर्ज करें"
        "note_optional" -> "नोट (वैकल्पिक)"
        "date" -> "तारीख"

        "spending_limit" -> "खर्च सीमा"
        "spending_limits" -> "खर्च सीमाएं"
        "set_spending_limit" -> "खर्च सीमा निर्धारित करें"
        "save_expense" -> "खर्च सेव करें"
        "update_expense" -> "खर्च अपडेट करें"
        "delete_expense" -> "खर्च हटाएं"
        "edit_expense" -> "खर्च संपादित करें"

        "total_expenses" -> "कुल खर्च"
        "total_spent" -> "कुल खर्च की गई राशि"
        "remaining_limit" -> "शेष सीमा"
        "expense_breakdown" -> "खर्च का विवरण"
        "expense_summary" -> "खर्च सारांश"
        "no_expenses" -> "अभी कोई खर्च नहीं जोड़ा गया है।"

        "expense_added" -> "खर्च सफलतापूर्वक जोड़ा गया।"
        "expense_updated" -> "खर्च सफलतापूर्वक अपडेट हुआ।"
        "expense_deleted" -> "खर्च सफलतापूर्वक हटाया गया।"

        "invalid_amount" -> "अमान्य राशि।"
        "amount_required" -> "राशि आवश्यक है।"
        "category_required" -> "श्रेणी आवश्यक है।"
        "category" -> "श्रेणी"
        "recent_expenses" -> "हाल के खर्च"

        "current_limit" -> "वर्तमान सीमा"
        "set_limit" -> "सीमा निर्धारित करें"
        "limit_amount" -> "सीमा राशि"

        "food" -> "भोजन"
        "travel" -> "यात्रा"
        "shopping" -> "खरीदारी"
        "bills" -> "बिल"
        "entertainment" -> "मनोरंजन"
        "other" -> "अन्य"

        "spending_limit_set" -> "खर्च सीमा सफलतापूर्वक निर्धारित हुई।"
        "current_spending" -> "वर्तमान खर्च"
        "limit" -> "सीमा"
        "remaining" -> "शेष"

        "smart_alert" -> "स्मार्ट अलर्ट"
        "smart_alerts" -> "स्मार्ट अलर्ट"
        "spending_alerts" -> "खर्च अलर्ट"
        "high_spending_alert" -> "अधिक खर्च अलर्ट"
        "category_limit_alert" -> "श्रेणी सीमा अलर्ट"
        "budget_alert" -> "बजट अलर्ट"
        "high_spending" -> "अधिक खर्च"
        "approaching_limit" -> "सीमा के करीब"

        "financial_health_tips" -> "वित्तीय स्वास्थ्य सुझाव"
        "financial_tips" -> "वित्तीय सुझाव"
        "tip" -> "सुझाव"
        "tips" -> "सुझाव"
        "save_money_tip" -> "अपने दैनिक खर्चों पर नज़र रखकर पैसे बचाएं।"
        "track_expenses_tip" -> "अपने खर्चों को नियमित रूप से ट्रैक करें।"
        "avoid_unnecessary_spending_tip" -> "अनावश्यक खर्च से बचें।"
        "build_emergency_fund" -> "आपातकालीन निधि बनाएं"
        "monitor_recurring" -> "बार-बार होने वाले खर्चों पर नज़र रखें"
        "avoid_new_emi" -> "अत्यधिक नई EMI से बचें"
        "set_savings_goal" -> "बचत का लक्ष्य निर्धारित करें"

        "monthly_insights" -> "मासिक जानकारी"
        "monthly_overview" -> "मासिक अवलोकन"
        "monthly_summary" -> "मासिक सारांश"
        "select_month" -> "महीना चुनें"
        "this_month" -> "यह महीना"
        "previous_month" -> "पिछला महीना"
        "next_month" -> "अगला महीना"
        "previous_months" -> "पिछले महीने"
        "current_month" -> "वर्तमान महीना"
        "total_monthly_spending" -> "कुल मासिक खर्च"
        "monthly_expenses" -> "मासिक खर्च"
        "total_spent_this_month" -> "इस महीने का कुल खर्च"
        "category_breakdown" -> "श्रेणी के अनुसार विवरण"
        "highest_spending_category" -> "सबसे अधिक खर्च वाली श्रेणी"
        "lowest_spending_category" -> "सबसे कम खर्च वाली श्रेणी"
        "average_daily_spending" -> "औसत दैनिक खर्च"
        "monthly_savings" -> "मासिक बचत"
        "spending_summary" -> "खर्च सारांश"
        "no_monthly_insights" -> "कोई मासिक जानकारी उपलब्ध नहीं है।"
        "monthly_insight_message" -> "यह आपके मासिक खर्च का अवलोकन है।"
        "no_data_for_month" -> "इस महीने के लिए कोई डेटा उपलब्ध नहीं है।"
        "monthly_total" -> "मासिक कुल"
        "monthly_average" -> "मासिक औसत"
        "daily_average" -> "दैनिक औसत"
        "top_category" -> "शीर्ष श्रेणी"
        "highest_expense" -> "सबसे अधिक खर्च"
        "lowest_expense" -> "सबसे कम खर्च"
        "expense_trend" -> "खर्च का रुझान"
        "spending_pattern" -> "खर्च का पैटर्न"
        "compare_months" -> "महीनों की तुलना करें"
        "monthly_report" -> "मासिक रिपोर्ट"
        "summary" -> "सारांश"

        "smart_financial_decisions" -> "स्मार्ट वित्तीय निर्णय"
        "make_smarter_decisions" -> "अपने वित्तीय डेटा का उपयोग करके बेहतर निर्णय लें।"
        "what_should_i_cut" -> "मुझे क्या कम करना चाहिए?"
        "what_if" -> "अगर ऐसा हो तो?"
        "see_reducing_spending" -> "देखें कि खर्च कम करने से आपके उपलब्ध पैसे पर क्या प्रभाव पड़ेगा।"
        "current_available_money" -> "वर्तमान उपलब्ध धन"
        "available_money" -> "उपलब्ध धन"
        "available_after_suggested_cuts" -> "सुझाए गए कटौती के बाद उपलब्ध धन"
        "smart_suggestions" -> "स्मार्ट सुझाव"
        "top_suggestions" -> "मुख्य सुझाव"
        "suggestions" -> "सुझाव"
        "recommendations" -> "सिफारिशें"
        "suggested_action" -> "सुझाई गई कार्रवाई"
        "potential_savings" -> "संभावित बचत"
        "potential_monthly_savings" -> "संभावित मासिक बचत"
        "potential_monthly_savings_zero" -> "संभावित मासिक बचत: {amount}"
        "add_expenses_personalized" -> "व्यक्तिगत सुझाव पाने के लिए पहले कुछ खर्च जोड़ें।"
        "no_major_reduction" -> "आपके वर्तमान खर्च में कोई बड़ा कटौती अवसर नहीं दिख रहा है।"
        "suggestions_current_spending" -> "सुझाव आपके वर्तमान खर्च के पैटर्न पर आधारित हैं।"
        "reduce_spending" -> "खर्च कम करें"
        "reduce_category" -> "श्रेणी का खर्च कम करें"
        "save_more" -> "अधिक बचत करें"
        "spending_habit" -> "खर्च करने की आदत"
        "financial_decision" -> "वित्तीय निर्णय"

        "cut_shopping" -> "खरीदारी कम करें"
        "cut_food" -> "भोजन खर्च कम करें"
        "cut_entertainment" -> "मनोरंजन खर्च कम करें"
        "cut_travel" -> "यात्रा खर्च कम करें"
        "cut_bills" -> "बिल खर्च कम करें"
        "cut_other" -> "अन्य खर्च कम करें"

        "simple_actions_financial_situation" -> "आपकी वित्तीय स्थिति के आधार पर सरल कार्य।"
        "set_aside_available_money" -> "हर महीने अपने उपलब्ध धन का कुछ हिस्सा अलग रखने का प्रयास करें।"
        "review_subscriptions" -> "सब्सक्रिप्शन और नियमित खर्चों की समीक्षा करें।"
        "check_existing_emi" -> "नई EMI जोड़ने से पहले अपनी मौजूदा EMI प्रतिबद्धता जांचें।"
        "use_savings_goal" -> "संभावित मासिक बचत को शुरुआती लक्ष्य बनाएं।"
        "set_monthly_limits" -> "अपनी श्रेणियों के लिए मासिक सीमाएं निर्धारित करें।"
        "spending_alerts_actual" -> "आपके वास्तविक खर्च के आधार पर खर्च अलर्ट।"
        "latest_entries" -> "आपकी नवीनतम प्रविष्टियां।"

        "cut" -> "कम करें"
        "suggested" -> "सुझाया गया"
        "spent" -> "खर्च"
        "keep_it_up" -> "इसी तरह जारी रखें!"
        "view_summary" -> "सारांश देखें"
        "apply_suggestion" -> "सुझाव लागू करें"

        "date_not_set" -> "तारीख निर्धारित नहीं है"
        "enter_valid_amount" -> "मान्य राशि दर्ज करें।"
        "enter_valid_limit" -> "मान्य सीमा दर्ज करें।"

        "where_did_you_spend" -> "आपने कहां खर्च किया?"
        "expense_note" -> "खर्च नोट"
        "today" -> "आज"
        "yesterday" -> "कल"

        "back" -> "वापस"

        else -> englishText(key)
    }
}


// ============================================================
// MARATHI
// ============================================================

fun marathiText(key: String): String {

    return when (key) {

        "paywise" -> "PayWise"
        "tagline" -> "समजूतदारपणे खर्च करा, शहाणपणाने बचत करा."

        "login" -> "लॉगिन"
        "signup" -> "नवीन खाते तयार करा"
        "create_account" -> "खाते तयार करा"
        "back_to_login" -> "लॉगिनवर परत जा"
        "create_your_account" -> "तुमचे खाते तयार करा"

        "full_name" -> "पूर्ण नाव"
        "email" -> "ईमेल आयडी"
        "password" -> "पासवर्ड"
        "confirm_password" -> "पासवर्डची पुष्टी करा"
        "mobile" -> "मोबाईल नंबर"

        "dashboard" -> "डॅशबोर्ड"
        "add_salary" -> "पगार जोडा"
        "my_emis" -> "माझ्या EMI"
        "add_emi" -> "EMI जोडा"
        "financial_summary" -> "आर्थिक सारांश"
        "profile" -> "प्रोफाइल"
        "settings" -> "सेटिंग्ज"
        "logout" -> "लॉगआउट"

        "welcome" -> "स्वागत आहे"
        "manage_finances" -> "तुमचे आर्थिक व्यवस्थापन शहाणपणाने करा."
        "loading_financial_data" -> "आर्थिक माहिती लोड होत आहे..."

        "monthly_salary" -> "मासिक पगार"
        "total_emi" -> "एकूण EMI"
        "remaining_salary" -> "उर्वरित पगार"
        "emi_ratio" -> "EMI प्रमाण"
        "financial_status" -> "आर्थिक स्थिती"
        "your_emis" -> "तुमच्या EMI"

        "no_emi_records" -> "कोणतेही EMI रेकॉर्ड सापडले नाहीत."
        "add_update_salary" -> "पगार जोडा / अपडेट करा"
        "save_salary" -> "पगार सेव्ह करा"
        "enter_valid_salary" -> "वैध पगार प्रविष्ट करा."
        "salary_updated" -> "पगार यशस्वीरित्या अपडेट झाला."

        "bank_lender" -> "बँक / कर्जदाता"
        "emi_amount" -> "EMI रक्कम"
        "due_date" -> "देय तारीख"
        "frequency" -> "वारंवारता"
        "monthly" -> "मासिक"

        "valid_emi" -> "कृपया वैध EMI माहिती प्रविष्ट करा."
        "emi_added" -> "EMI यशस्वीरित्या जोडली."
        "no_financial_data" -> "आर्थिक माहिती उपलब्ध नाही."

        "my_profile" -> "माझे प्रोफाइल"
        "profile_information" -> "प्रोफाइल माहिती"
        "personal_information" -> "वैयक्तिक माहिती"
        "not_available" -> "उपलब्ध नाही"

        "notifications" -> "सूचना"
        "emi_due_reminders" -> "EMI देय तारीख स्मरणपत्रे"
        "payment_reminders" -> "पेमेंट स्मरणपत्रे"
        "reminder_days" -> "स्मरणपत्राचे दिवस"
        "days" -> "दिवस"

        "financial_preferences" -> "आर्थिक प्राधान्ये"
        "currency" -> "चलन"
        "primary_currency" -> "प्राथमिक चलन"
        "show_financial_status" -> "आर्थिक स्थिती दाखवा"

        "language" -> "भाषा"
        "app_language" -> "अॅप भाषा"
        "region" -> "प्रदेश"
        "country_region" -> "देश / प्रदेश"

        "about_paywise" -> "PayWise बद्दल"
        "version" -> "आवृत्ती 1.0"

        "select_currency" -> "चलन निवडा"
        "search_currency" -> "चलन किंवा कोड शोधा"
        "select_country" -> "देश / प्रदेश निवडा"
        "search_country" -> "देश शोधा"
        "select_language" -> "भाषा निवडा"
        "search_language" -> "भाषा शोधा"

        "no_results" -> "परिणाम सापडला नाही"
        "close" -> "बंद करा"

        "please_fill_all" -> "कृपया सर्व फील्ड भरा."
        "passwords_not_match" -> "पासवर्ड जुळत नाहीत."
        "please_enter_login" -> "कृपया ईमेल आणि पासवर्ड प्रविष्ट करा."

        "profile_photo" -> "प्रोफाइल फोटो"
        "select_photo" -> "फोटो निवडा"
        "edit_profile" -> "प्रोफाइल संपादित करा"
        "financial_profile" -> "आर्थिक प्रोफाइल"
        "total_active_emis" -> "एकूण सक्रिय EMI"
        "total_monthly_emi" -> "एकूण मासिक EMI बांधिलकी"

        "account_information" -> "खाते माहिती"
        "account_creation_date" -> "खाते तयार करण्याची तारीख"
        "user_id" -> "यूजर आयडी"

        "security" -> "सुरक्षा"
        "change_password" -> "पासवर्ड बदला"
        "current_password" -> "सध्याचा पासवर्ड"
        "new_password" -> "नवीन पासवर्ड"
        "save_changes" -> "बदल सेव्ह करा"
        "cancel" -> "रद्द करा"

        "financial_overview" -> "आर्थिक आढावा"
        "payment_reminder" -> "पेमेंट स्मरणपत्र"
        "no_valid_emi_due_date" -> "वैध EMI देय तारीख उपलब्ध नाही"
        "emi_was_due_on" -> "EMI {date} रोजी देय होती"
        "emi_due_today" -> "EMI आज देय आहे"
        "reminder_due_in" -> "स्मरणपत्र: {lender} ची EMI {days} दिवसांत देय आहे"
        "reminder_will_appear" -> "देय तारखेच्या {days} दिवस आधी स्मरणपत्र दिसेल"
        "due" -> "देय"
        "remind_me" -> "मला स्मरण करून द्या"
        "test_notification" -> "चाचणी सूचना"

        "everyday_expenses" -> "दैनंदिन खर्च"
        "track_daily_spending" -> "तुमचा दैनंदिन खर्च ट्रॅक करा, त्याचे विश्लेषण करा आणि अधिक बचत करा."
        "add_expense" -> "खर्च जोडा"
        "expense_category" -> "खर्चाची श्रेणी"
        "select_category" -> "श्रेणी निवडा"
        "amount" -> "रक्कम"
        "enter_amount" -> "रक्कम प्रविष्ट करा"
        "note" -> "नोंद"
        "enter_note" -> "नोंद प्रविष्ट करा"
        "note_optional" -> "नोंद (ऐच्छिक)"
        "date" -> "तारीख"

        "spending_limit" -> "खर्च मर्यादा"
        "spending_limits" -> "खर्च मर्यादा"
        "set_spending_limit" -> "खर्च मर्यादा सेट करा"
        "save_expense" -> "खर्च सेव्ह करा"
        "update_expense" -> "खर्च अपडेट करा"
        "delete_expense" -> "खर्च हटवा"
        "edit_expense" -> "खर्च संपादित करा"

        "total_expenses" -> "एकूण खर्च"
        "total_spent" -> "एकूण खर्च"
        "remaining_limit" -> "उर्वरित मर्यादा"
        "expense_breakdown" -> "खर्चाचे वर्गीकरण"
        "expense_summary" -> "खर्च सारांश"
        "no_expenses" -> "अजून कोणताही खर्च जोडलेला नाही."

        "expense_added" -> "खर्च यशस्वीरित्या जोडला."
        "expense_updated" -> "खर्च यशस्वीरित्या अपडेट झाला."
        "expense_deleted" -> "खर्च यशस्वीरित्या हटवला."

        "invalid_amount" -> "अवैध रक्कम."
        "amount_required" -> "रक्कम आवश्यक आहे."
        "category_required" -> "श्रेणी आवश्यक आहे."
        "category" -> "श्रेणी"
        "recent_expenses" -> "अलीकडील खर्च"

        "current_limit" -> "सध्याची मर्यादा"
        "set_limit" -> "मर्यादा सेट करा"
        "limit_amount" -> "मर्यादा रक्कम"

        "food" -> "अन्न"
        "travel" -> "प्रवास"
        "shopping" -> "खरेदी"
        "bills" -> "बिले"
        "entertainment" -> "मनोरंजन"
        "other" -> "इतर"

        "spending_limit_set" -> "खर्च मर्यादा यशस्वीरित्या सेट झाली."
        "current_spending" -> "सध्याचा खर्च"
        "limit" -> "मर्यादा"
        "remaining" -> "उर्वरित"

        "smart_alert" -> "स्मार्ट अलर्ट"
        "smart_alerts" -> "स्मार्ट अलर्ट"
        "spending_alerts" -> "खर्च अलर्ट"
        "high_spending_alert" -> "जास्त खर्चाचा अलर्ट"
        "category_limit_alert" -> "श्रेणी मर्यादा अलर्ट"
        "budget_alert" -> "बजेट अलर्ट"
        "high_spending" -> "जास्त खर्च"
        "approaching_limit" -> "मर्यादेच्या जवळ"

        "financial_health_tips" -> "आर्थिक आरोग्य टिप्स"
        "financial_tips" -> "आर्थिक टिप्स"
        "tip" -> "टिप"
        "tips" -> "टिप्स"
        "save_money_tip" -> "दैनंदिन खर्चावर लक्ष ठेवून पैसे वाचवा."
        "track_expenses_tip" -> "तुमचा खर्च नियमितपणे ट्रॅक करा."
        "avoid_unnecessary_spending_tip" -> "अनावश्यक खर्च टाळा."
        "build_emergency_fund" -> "आपत्कालीन निधी तयार करा"
        "monitor_recurring" -> "नियमित होणाऱ्या खर्चांवर लक्ष ठेवा"
        "avoid_new_emi" -> "अतिरिक्त नवीन EMI टाळा"
        "set_savings_goal" -> "बचतीचे लक्ष्य सेट करा"

        "monthly_insights" -> "मासिक माहिती"
        "monthly_overview" -> "मासिक आढावा"
        "monthly_summary" -> "मासिक सारांश"
        "select_month" -> "महिना निवडा"
        "this_month" -> "हा महिना"
        "previous_month" -> "मागील महिना"
        "next_month" -> "पुढील महिना"
        "previous_months" -> "मागील महिने"
        "current_month" -> "सध्याचा महिना"
        "total_monthly_spending" -> "एकूण मासिक खर्च"
        "monthly_expenses" -> "मासिक खर्च"
        "total_spent_this_month" -> "या महिन्यातील एकूण खर्च"
        "category_breakdown" -> "श्रेणीनुसार खर्च"
        "highest_spending_category" -> "सर्वाधिक खर्चाची श्रेणी"
        "lowest_spending_category" -> "सर्वात कमी खर्चाची श्रेणी"
        "average_daily_spending" -> "सरासरी दैनिक खर्च"
        "monthly_savings" -> "मासिक बचत"
        "spending_summary" -> "खर्च सारांश"
        "no_monthly_insights" -> "मासिक माहिती उपलब्ध नाही."
        "monthly_insight_message" -> "तुमच्या मासिक खर्चाचा आढावा येथे आहे."
        "no_data_for_month" -> "या महिन्यासाठी कोणताही डेटा उपलब्ध नाही."
        "monthly_total" -> "मासिक एकूण"
        "monthly_average" -> "मासिक सरासरी"
        "daily_average" -> "दैनिक सरासरी"
        "top_category" -> "सर्वाधिक खर्चाची श्रेणी"
        "highest_expense" -> "सर्वाधिक खर्च"
        "lowest_expense" -> "सर्वात कमी खर्च"
        "expense_trend" -> "खर्चाचा कल"
        "spending_pattern" -> "खर्चाचा नमुना"
        "compare_months" -> "महिन्यांची तुलना करा"
        "monthly_report" -> "मासिक अहवाल"
        "summary" -> "सारांश"

        "smart_financial_decisions" -> "स्मार्ट आर्थिक निर्णय"
        "make_smarter_decisions" -> "तुमच्या आर्थिक डेटाचा वापर करून अधिक चांगले निर्णय घ्या."
        "what_should_i_cut" -> "मी काय कमी करावे?"
        "what_if" -> "जर असे केले तर?"
        "see_reducing_spending" -> "खर्च कमी केल्याने उपलब्ध पैशांवर काय परिणाम होतो ते पहा."
        "current_available_money" -> "सध्याचे उपलब्ध पैसे"
        "available_money" -> "उपलब्ध पैसे"
        "available_after_suggested_cuts" -> "सुचवलेल्या कपातीनंतर उपलब्ध पैसे"
        "smart_suggestions" -> "स्मार्ट सूचना"
        "top_suggestions" -> "मुख्य सूचना"
        "suggestions" -> "सूचना"
        "recommendations" -> "शिफारसी"
        "suggested_action" -> "सुचवलेली कृती"
        "potential_savings" -> "संभाव्य बचत"
        "potential_monthly_savings" -> "संभाव्य मासिक बचत"
        "potential_monthly_savings_zero" -> "संभाव्य मासिक बचत: {amount}"

        "add_expenses_personalized" -> "वैयक्तिक सूचना मिळवण्यासाठी आधी काही खर्च जोडा."
        "no_major_reduction" -> "तुमच्या सध्याच्या खर्चात मोठी कपात करण्याची संधी दिसत नाही."
        "suggestions_current_spending" -> "सूचना तुमच्या सध्याच्या खर्चाच्या पद्धतीवर आधारित आहेत."

        "reduce_spending" -> "खर्च कमी करा"
        "reduce_category" -> "श्रेणीतील खर्च कमी करा"
        "save_more" -> "अधिक बचत करा"
        "spending_habit" -> "खर्चाची सवय"
        "financial_decision" -> "आर्थिक निर्णय"

        "cut_shopping" -> "खरेदी कमी करा"
        "cut_food" -> "अन्नावरील खर्च कमी करा"
        "cut_entertainment" -> "मनोरंजनावरील खर्च कमी करा"
        "cut_travel" -> "प्रवासाचा खर्च कमी करा"
        "cut_bills" -> "बिलांचा खर्च कमी करा"
        "cut_other" -> "इतर खर्च कमी करा"

        "simple_actions_financial_situation" -> "तुमच्या आर्थिक स्थितीनुसार सोप्या कृती."
        "set_aside_available_money" -> "दर महिन्याला उपलब्ध पैशांपैकी काही रक्कम बाजूला ठेवा."
        "review_subscriptions" -> "सबस्क्रिप्शन आणि नियमित खर्चांची नियमितपणे समीक्षा करा."
        "check_existing_emi" -> "नवीन EMI घेण्यापूर्वी सध्याच्या EMI ची जबाबदारी तपासा."
        "use_savings_goal" -> "संभाव्य मासिक बचतीला सुरुवातीचे बचत लक्ष्य बनवा."
        "set_monthly_limits" -> "प्रत्येक खर्चाच्या श्रेणीसाठी मासिक मर्यादा सेट करा."
        "spending_alerts_actual" -> "तुमच्या वास्तविक खर्चावर आधारित अलर्ट."
        "latest_entries" -> "तुमच्या अलीकडील नोंदी."

        "cut" -> "कमी करा"
        "suggested" -> "सुचवलेले"
        "spent" -> "खर्च"
        "keep_it_up" -> "असेच सुरू ठेवा!"
        "view_summary" -> "सारांश पहा"
        "apply_suggestion" -> "सूचना लागू करा"

        "date_not_set" -> "तारीख सेट केलेली नाही"
        "enter_valid_amount" -> "वैध रक्कम प्रविष्ट करा."
        "enter_valid_limit" -> "वैध मर्यादा प्रविष्ट करा."

        "where_did_you_spend" -> "तुम्ही कुठे खर्च केला?"
        "expense_note" -> "खर्चाची नोंद"
        "today" -> "आज"
        "yesterday" -> "काल"

        "back" -> "मागे"

        else -> englishText(key)
    }
}


// ============================================================
// GUJARATI
// ============================================================

fun gujaratiText(key: String): String {

    return when (key) {

        "paywise" -> "PayWise"
        "tagline" -> "સમજદારીથી ખર્ચ કરો, સમજદારીથી બચત કરો."

        "login" -> "લૉગિન"
        "signup" -> "નવું ખાતું બનાવો"
        "create_account" -> "ખાતું બનાવો"
        "back_to_login" -> "લૉગિન પર પાછા જાઓ"
        "create_your_account" -> "તમારું ખાતું બનાવો"

        "full_name" -> "પૂરું નામ"
        "email" -> "ઈમેલ આઈડી"
        "password" -> "પાસવર્ડ"
        "confirm_password" -> "પાસવર્ડની પુષ્ટિ કરો"
        "mobile" -> "મોબાઇલ નંબર"

        "dashboard" -> "ડેશબોર્ડ"
        "add_salary" -> "પગાર ઉમેરો"
        "my_emis" -> "મારી EMI"
        "add_emi" -> "EMI ઉમેરો"
        "financial_summary" -> "નાણાકીય સારાંશ"
        "profile" -> "પ્રોફાઇલ"
        "settings" -> "સેટિંગ્સ"
        "logout" -> "લૉગઆઉટ"

        "welcome" -> "સ્વાગત છે"
        "manage_finances" -> "તમારા નાણાંનું સમજદારીપૂર્વક સંચાલન કરો."
        "loading_financial_data" -> "નાણાકીય માહિતી લોડ થઈ રહી છે..."

        "monthly_salary" -> "માસિક પગાર"
        "total_emi" -> "કુલ EMI"
        "remaining_salary" -> "બાકી પગાર"
        "emi_ratio" -> "EMI ગુણોત્તર"
        "financial_status" -> "નાણાકીય સ્થિતિ"
        "your_emis" -> "તમારી EMI"

        "no_emi_records" -> "કોઈ EMI રેકોર્ડ મળ્યો નથી."
        "add_update_salary" -> "પગાર ઉમેરો / અપડેટ કરો"
        "save_salary" -> "પગાર સાચવો"
        "enter_valid_salary" -> "માન્ય પગાર દાખલ કરો."
        "salary_updated" -> "પગાર સફળતાપૂર્વક અપડેટ થયો."

        "bank_lender" -> "બેંક / ધિરાણકર્તા"
        "emi_amount" -> "EMI રકમ"
        "due_date" -> "નિયત તારીખ"
        "frequency" -> "આવર્તન"
        "monthly" -> "માસિક"

        "valid_emi" -> "કૃપા કરીને માન્ય EMI વિગતો દાખલ કરો."
        "emi_added" -> "EMI સફળતાપૂર્વક ઉમેરાઈ."
        "no_financial_data" -> "નાણાકીય માહિતી ઉપલબ્ધ નથી."

        "my_profile" -> "મારી પ્રોફાઇલ"
        "profile_information" -> "પ્રોફાઇલ માહિતી"
        "personal_information" -> "વ્યક્તિગત માહિતી"
        "not_available" -> "ઉપલબ્ધ નથી"

        "notifications" -> "સૂચનાઓ"
        "emi_due_reminders" -> "EMI નિયત તારીખ રિમાઇન્ડર"
        "payment_reminders" -> "ચુકવણી રિમાઇન્ડર"
        "reminder_days" -> "રિમાઇન્ડરના દિવસો"
        "days" -> "દિવસ"

        "financial_preferences" -> "નાણાકીય પસંદગીઓ"
        "currency" -> "ચલણ"
        "primary_currency" -> "પ્રાથમિક ચલણ"
        "show_financial_status" -> "નાણાકીય સ્થિતિ બતાવો"

        "language" -> "ભાષા"
        "app_language" -> "એપ ભાષા"
        "region" -> "પ્રદેશ"
        "country_region" -> "દેશ / પ્રદેશ"

        "about_paywise" -> "PayWise વિશે"
        "version" -> "સંસ્કરણ 1.0"

        "select_currency" -> "ચલણ પસંદ કરો"
        "search_currency" -> "ચલણ અથવા કોડ શોધો"
        "select_country" -> "દેશ / પ્રદેશ પસંદ કરો"
        "search_country" -> "દેશ શોધો"
        "select_language" -> "ભાષા પસંદ કરો"
        "search_language" -> "ભાષા શોધો"

        "no_results" -> "કોઈ પરિણામ મળ્યું નથી"
        "close" -> "બંધ કરો"

        "please_fill_all" -> "કૃપા કરીને બધા ફીલ્ડ ભરો."
        "passwords_not_match" -> "પાસવર્ડ મેળ ખાતા નથી."
        "please_enter_login" -> "કૃપા કરીને ઈમેલ અને પાસવર્ડ દાખલ કરો."

        "profile_photo" -> "પ્રોફાઇલ ફોટો"
        "select_photo" -> "ફોટો પસંદ કરો"
        "edit_profile" -> "પ્રોફાઇલ સંપાદિત કરો"
        "financial_profile" -> "નાણાકીય પ્રોફાઇલ"
        "total_active_emis" -> "કુલ સક્રિય EMI"
        "total_monthly_emi" -> "કુલ માસિક EMI પ્રતિબદ્ધતા"

        "account_information" -> "ખાતાની માહિતી"
        "account_creation_date" -> "ખાતું બનાવવાની તારીખ"
        "user_id" -> "યુઝર આઈડી"

        "security" -> "સુરક્ષા"
        "change_password" -> "પાસવર્ડ બદલો"
        "current_password" -> "વર્તમાન પાસવર્ડ"
        "new_password" -> "નવો પાસવર્ડ"
        "save_changes" -> "ફેરફારો સાચવો"
        "cancel" -> "રદ કરો"

        "financial_overview" -> "નાણાકીય ઝાંખી"
        "payment_reminder" -> "ચુકવણી રિમાઇન્ડર"
        "no_valid_emi_due_date" -> "માન્ય EMI નિયત તારીખ ઉપલબ્ધ નથી"
        "emi_was_due_on" -> "EMI {date} ના રોજ ચૂકવવાની હતી"
        "emi_due_today" -> "EMI આજે ચૂકવવાની છે"
        "reminder_due_in" -> "રિમાઇન્ડર: {lender} ની EMI {days} દિવસમાં ચૂકવવાની છે"
        "reminder_will_appear" -> "રિમાઇન્ડર નિયત તારીખના {days} દિવસ પહેલાં દેખાશે"
        "due" -> "ચૂકવવાની"
        "remind_me" -> "મને યાદ કરાવો"
        "test_notification" -> "ટેસ્ટ સૂચના"

        "everyday_expenses" -> "દૈનિક ખર્ચ"
        "track_daily_spending" -> "તમારા દૈનિક ખર્ચને ટ્રૅક કરો, તેનું વિશ્લેષણ કરો અને વધુ બચત કરો."
        "add_expense" -> "ખર્ચ ઉમેરો"
        "expense_category" -> "ખર્ચની શ્રેણી"
        "select_category" -> "શ્રેણી પસંદ કરો"
        "amount" -> "રકમ"
        "enter_amount" -> "રકમ દાખલ કરો"
        "note" -> "નોંધ"
        "enter_note" -> "નોંધ દાખલ કરો"
        "note_optional" -> "નોંધ (વૈકલ્પિક)"
        "date" -> "તારીખ"

        "spending_limit" -> "ખર્ચ મર્યાદા"
        "spending_limits" -> "ખર્ચ મર્યાદાઓ"
        "set_spending_limit" -> "ખર્ચ મર્યાદા સેટ કરો"
        "save_expense" -> "ખર્ચ સાચવો"
        "update_expense" -> "ખર્ચ અપડેટ કરો"
        "delete_expense" -> "ખર્ચ કાઢી નાખો"
        "edit_expense" -> "ખર્ચ સંપાદિત કરો"

        "total_expenses" -> "કુલ ખર્ચ"
        "total_spent" -> "કુલ ખર્ચ"
        "remaining_limit" -> "બાકી મર્યાદા"
        "expense_breakdown" -> "ખર્ચનું વિભાજન"
        "expense_summary" -> "ખર્ચનો સારાંશ"
        "no_expenses" -> "હજુ સુધી કોઈ ખર્ચ ઉમેરવામાં આવ્યો નથી."

        "expense_added" -> "ખર્ચ સફળતાપૂર્વક ઉમેરાયો."
        "expense_updated" -> "ખર્ચ સફળતાપૂર્વક અપડેટ થયો."
        "expense_deleted" -> "ખર્ચ સફળતાપૂર્વક કાઢી નાખવામાં આવ્યો."

        "invalid_amount" -> "અમાન્ય રકમ."
        "amount_required" -> "રકમ જરૂરી છે."
        "category_required" -> "શ્રેણી જરૂરી છે."
        "category" -> "શ્રેણી"
        "recent_expenses" -> "તાજેતરના ખર્ચ"

        "current_limit" -> "વર્તમાન મર્યાદા"
        "set_limit" -> "મર્યાદા સેટ કરો"
        "limit_amount" -> "મર્યાદાની રકમ"

        "food" -> "ખોરાક"
        "travel" -> "પ્રવાસ"
        "shopping" -> "ખરીદી"
        "bills" -> "બિલ"
        "entertainment" -> "મનોરંજન"
        "other" -> "અન્ય"

        "spending_limit_set" -> "ખર્ચ મર્યાદા સફળતાપૂર્વક સેટ થઈ."
        "current_spending" -> "વર્તમાન ખર્ચ"
        "limit" -> "મર્યાદા"
        "remaining" -> "બાકી"

        "smart_alert" -> "સ્માર્ટ અલર્ટ"
        "smart_alerts" -> "સ્માર્ટ અલર્ટ્સ"
        "spending_alerts" -> "ખર્ચ અલર્ટ્સ"
        "high_spending_alert" -> "વધુ ખર્ચ અલર્ટ"
        "category_limit_alert" -> "શ્રેણી મર્યાદા અલર્ટ"
        "budget_alert" -> "બજેટ અલર્ટ"
        "high_spending" -> "વધુ ખર્ચ"
        "approaching_limit" -> "મર્યાદાની નજીક"

        "financial_health_tips" -> "નાણાકીય આરોગ્ય ટીપ્સ"
        "financial_tips" -> "નાણાકીય ટીપ્સ"
        "tip" -> "ટીપ"
        "tips" -> "ટીપ્સ"
        "save_money_tip" -> "તમારા દૈનિક ખર્ચ પર નજર રાખીને પૈસા બચાવો."
        "track_expenses_tip" -> "તમારા ખર્ચને નિયમિત રીતે ટ્રૅક કરો."
        "avoid_unnecessary_spending_tip" -> "બિનજરૂરી ખર્ચ ટાળો."
        "build_emergency_fund" -> "કટોકટી ભંડોળ બનાવો"
        "monitor_recurring" -> "નિયમિત ખર્ચ પર નજર રાખો"
        "avoid_new_emi" -> "વધુ નવી EMI ટાળો"
        "set_savings_goal" -> "બચતનું લક્ષ્ય સેટ કરો"

        "monthly_insights" -> "માસિક માહિતી"
        "monthly_overview" -> "માસિક ઝાંખી"
        "monthly_summary" -> "માસિક સારાંશ"
        "select_month" -> "મહિનો પસંદ કરો"
        "this_month" -> "આ મહિનો"
        "previous_month" -> "પાછલો મહિનો"
        "next_month" -> "આગળનો મહિનો"
        "previous_months" -> "પાછલા મહિના"
        "current_month" -> "વર્તમાન મહિનો"
        "total_monthly_spending" -> "કુલ માસિક ખર્ચ"
        "monthly_expenses" -> "માસિક ખર્ચ"
        "total_spent_this_month" -> "આ મહિનાનો કુલ ખર્ચ"
        "category_breakdown" -> "શ્રેણી મુજબ વિભાજન"
        "highest_spending_category" -> "સૌથી વધુ ખર્ચની શ્રેણી"
        "lowest_spending_category" -> "સૌથી ઓછા ખર્ચની શ્રેણી"
        "average_daily_spending" -> "સરેરાશ દૈનિક ખર્ચ"
        "monthly_savings" -> "માસિક બચત"
        "spending_summary" -> "ખર્ચનો સારાંશ"
        "no_monthly_insights" -> "માસિક માહિતી ઉપલબ્ધ નથી."
        "monthly_insight_message" -> "તમારા માસિક ખર્ચની ઝાંખી અહીં છે."
        "no_data_for_month" -> "આ મહિના માટે કોઈ ડેટા ઉપલબ્ધ નથી."
        "monthly_total" -> "માસિક કુલ"
        "monthly_average" -> "માસિક સરેરાશ"
        "daily_average" -> "દૈનિક સરેરાશ"
        "top_category" -> "ટોચની શ્રેણી"
        "highest_expense" -> "સૌથી વધુ ખર્ચ"
        "lowest_expense" -> "સૌથી ઓછો ખર્ચ"
        "expense_trend" -> "ખર્ચનો ટ્રેન્ડ"
        "spending_pattern" -> "ખર્ચની પેટર્ન"
        "compare_months" -> "મહિનાઓની તુલના કરો"
        "monthly_report" -> "માસિક રિપોર્ટ"
        "summary" -> "સારાંશ"

        "smart_financial_decisions" -> "સ્માર્ટ નાણાકીય નિર્ણયો"
        "make_smarter_decisions" -> "તમારા નાણાકીય ડેટાનો ઉપયોગ કરીને વધુ સારા નિર્ણયો લો."
        "what_should_i_cut" -> "મારે શું ઘટાડવું જોઈએ?"
        "what_if" -> "જો આવું થાય તો?"
        "see_reducing_spending" -> "ખર્ચ ઘટાડવાથી તમારા ઉપલબ્ધ પૈસા પર શું અસર થશે તે જુઓ."
        "current_available_money" -> "વર્તમાન ઉપલબ્ધ પૈસા"
        "available_money" -> "ઉપલબ્ધ પૈસા"
        "available_after_suggested_cuts" -> "સૂચવેલા ઘટાડા પછી ઉપલબ્ધ પૈસા"
        "smart_suggestions" -> "સ્માર્ટ સૂચનો"
        "top_suggestions" -> "મુખ્ય સૂચનો"
        "suggestions" -> "સૂચનો"
        "recommendations" -> "ભલામણો"
        "suggested_action" -> "સૂચવેલી કાર્યવાહી"
        "potential_savings" -> "સંભવિત બચત"
        "potential_monthly_savings" -> "સંભવિત માસિક બચત"
        "potential_monthly_savings_zero" -> "સંભવિત માસિક બચત: {amount}"

        "add_expenses_personalized" -> "વ્યક્તિગત સૂચનો મેળવવા માટે પહેલા કેટલાક ખર્ચ ઉમેરો."
        "no_major_reduction" -> "તમારા વર્તમાન ખર્ચમાં કોઈ મોટો ઘટાડો કરવાની તક દેખાતી નથી."
        "suggestions_current_spending" -> "સૂચનો તમારા વર્તમાન ખર્ચની પેટર્ન પર આધારિત છે."

        "reduce_spending" -> "ખર્ચ ઘટાડો"
        "reduce_category" -> "શ્રેણીનો ખર્ચ ઘટાડો"
        "save_more" -> "વધુ બચત કરો"
        "spending_habit" -> "ખર્ચ કરવાની આદત"
        "financial_decision" -> "નાણાકીય નિર્ણય"

        "cut_shopping" -> "ખરીદી ઘટાડો"
        "cut_food" -> "ખોરાકનો ખર્ચ ઘટાડો"
        "cut_entertainment" -> "મનોરંજનનો ખર્ચ ઘટાડો"
        "cut_travel" -> "પ્રવાસનો ખર્ચ ઘટાડો"
        "cut_bills" -> "બિલનો ખર્ચ ઘટાડો"
        "cut_other" -> "અન્ય ખર્ચ ઘટાડો"

        "simple_actions_financial_situation" -> "તમારી નાણાકીય સ્થિતિના આધારે સરળ પગલાં."
        "set_aside_available_money" -> "દર મહિને તમારા ઉપલબ્ધ પૈસાનો થોડો ભાગ અલગ રાખવાનો પ્રયાસ કરો."
        "review_subscriptions" -> "સબ્સ્ક્રિપ્શન અને નિયમિત ખર્ચની નિયમિત સમીક્ષા કરો."
        "check_existing_emi" -> "નવી EMI ઉમેરતા પહેલાં તમારી હાલની EMI પ્રતિબદ્ધતા તપાસો."
        "use_savings_goal" -> "તમારી સંભવિત માસિક બચતને શરૂઆતના લક્ષ્ય તરીકે ઉપયોગ કરો."
        "set_monthly_limits" -> "તમારી ખર્ચની શ્રેણીઓ માટે માસિક મર્યાદા સેટ કરો."
        "spending_alerts_actual" -> "તમારા વાસ્તવિક ખર્ચ પર આધારિત ખર્ચ અલર્ટ્સ."
        "latest_entries" -> "તમારી તાજેતરની એન્ટ્રીઓ."

        "cut" -> "ઘટાડો"
        "suggested" -> "સૂચવેલ"
        "spent" -> "ખર્ચ"
        "keep_it_up" -> "આ રીતે જ ચાલુ રાખો!"
        "view_summary" -> "સારાંશ જુઓ"
        "apply_suggestion" -> "સૂચન લાગુ કરો"

        "date_not_set" -> "તારીખ સેટ નથી"
        "enter_valid_amount" -> "માન્ય રકમ દાખલ કરો."
        "enter_valid_limit" -> "માન્ય મર્યાદા દાખલ કરો."

        "where_did_you_spend" -> "તમે ક્યાં ખર્ચ કર્યો?"
        "expense_note" -> "ખર્ચની નોંધ"
        "today" -> "આજે"
        "yesterday" -> "ગઈકાલે"

        "back" -> "પાછા"

        else -> englishText(key)
    }
}


// ============================================================
// BENGALI
// ============================================================

fun bengaliText(key: String): String {

    return when (key) {

        "paywise" -> "PayWise"
        "tagline" -> "বুদ্ধিমানের মতো খরচ করুন, বুদ্ধিমানের মতো সঞ্চয় করুন।"

        "login" -> "লগইন"
        "signup" -> "নতুন অ্যাকাউন্ট তৈরি করুন"
        "create_account" -> "অ্যাকাউন্ট তৈরি করুন"
        "back_to_login" -> "লগইনে ফিরে যান"
        "create_your_account" -> "আপনার অ্যাকাউন্ট তৈরি করুন"

        "full_name" -> "পূর্ণ নাম"
        "email" -> "ইমেল আইডি"
        "password" -> "পাসওয়ার্ড"
        "confirm_password" -> "পাসওয়ার্ড নিশ্চিত করুন"
        "mobile" -> "মোবাইল নম্বর"

        "dashboard" -> "ড্যাশবোর্ড"
        "add_salary" -> "বেতন যোগ করুন"
        "my_emis" -> "আমার EMI"
        "add_emi" -> "EMI যোগ করুন"
        "financial_summary" -> "আর্থিক সারাংশ"
        "profile" -> "প্রোফাইল"
        "settings" -> "সেটিংস"
        "logout" -> "লগআউট"

        "welcome" -> "স্বাগতম"
        "manage_finances" -> "আপনার অর্থ বুদ্ধিমানের মতো পরিচালনা করুন।"
        "loading_financial_data" -> "আর্থিক তথ্য লোড হচ্ছে..."

        "monthly_salary" -> "মাসিক বেতন"
        "total_emi" -> "মোট EMI"
        "remaining_salary" -> "অবশিষ্ট বেতন"
        "emi_ratio" -> "EMI অনুপাত"
        "financial_status" -> "আর্থিক অবস্থা"
        "your_emis" -> "আপনার EMI"

        "no_emi_records" -> "কোনো EMI রেকর্ড পাওয়া যায়নি।"
        "add_update_salary" -> "বেতন যোগ / আপডেট করুন"
        "save_salary" -> "বেতন সংরক্ষণ করুন"
        "enter_valid_salary" -> "একটি বৈধ বেতন লিখুন।"
        "salary_updated" -> "বেতন সফলভাবে আপডেট হয়েছে।"

        "bank_lender" -> "ব্যাংক / ঋণদাতা"
        "emi_amount" -> "EMI পরিমাণ"
        "due_date" -> "নির্ধারিত তারিখ"
        "frequency" -> "ফ্রিকোয়েন্সি"
        "monthly" -> "মাসিক"

        "valid_emi" -> "অনুগ্রহ করে বৈধ EMI তথ্য লিখুন।"
        "emi_added" -> "EMI সফলভাবে যোগ হয়েছে।"
        "no_financial_data" -> "কোনো আর্থিক তথ্য পাওয়া যায়নি।"

        "my_profile" -> "আমার প্রোফাইল"
        "profile_information" -> "প্রোফাইল তথ্য"
        "personal_information" -> "ব্যক্তিগত তথ্য"
        "not_available" -> "উপলব্ধ নয়"

        "notifications" -> "বিজ্ঞপ্তি"
        "emi_due_reminders" -> "EMI নির্ধারিত তারিখের অনুস্মারক"
        "payment_reminders" -> "পেমেন্ট অনুস্মারক"
        "reminder_days" -> "অনুস্মারকের দিন"
        "days" -> "দিন"

        "financial_preferences" -> "আর্থিক পছন্দ"
        "currency" -> "মুদ্রা"
        "primary_currency" -> "প্রধান মুদ্রা"
        "show_financial_status" -> "আর্থিক অবস্থা দেখান"

        "language" -> "ভাষা"
        "app_language" -> "অ্যাপের ভাষা"
        "region" -> "অঞ্চল"
        "country_region" -> "দেশ / অঞ্চল"

        "about_paywise" -> "PayWise সম্পর্কে"
        "version" -> "সংস্করণ 1.0"

        "select_currency" -> "মুদ্রা নির্বাচন করুন"
        "search_currency" -> "মুদ্রা বা কোড খুঁজুন"
        "select_country" -> "দেশ / অঞ্চল নির্বাচন করুন"
        "search_country" -> "দেশ খুঁজুন"
        "select_language" -> "ভাষা নির্বাচন করুন"
        "search_language" -> "ভাষা খুঁজুন"

        "no_results" -> "কোনো ফলাফল পাওয়া যায়নি"
        "close" -> "বন্ধ করুন"

        "please_fill_all" -> "অনুগ্রহ করে সব ক্ষেত্র পূরণ করুন।"
        "passwords_not_match" -> "পাসওয়ার্ড মিলছে না।"
        "please_enter_login" -> "অনুগ্রহ করে ইমেল এবং পাসওয়ার্ড লিখুন।"

        "profile_photo" -> "প্রোফাইল ছবি"
        "select_photo" -> "ছবি নির্বাচন করুন"
        "edit_profile" -> "প্রোফাইল সম্পাদনা করুন"
        "financial_profile" -> "আর্থিক প্রোফাইল"
        "total_active_emis" -> "মোট সক্রিয় EMI"
        "total_monthly_emi" -> "মোট মাসিক EMI প্রতিশ্রুতি"

        "account_information" -> "অ্যাকাউন্টের তথ্য"
        "account_creation_date" -> "অ্যাকাউন্ট তৈরির তারিখ"
        "user_id" -> "ইউজার আইডি"

        "security" -> "নিরাপত্তা"
        "change_password" -> "পাসওয়ার্ড পরিবর্তন করুন"
        "current_password" -> "বর্তমান পাসওয়ার্ড"
        "new_password" -> "নতুন পাসওয়ার্ড"
        "save_changes" -> "পরিবর্তন সংরক্ষণ করুন"
        "cancel" -> "বাতিল করুন"

        "financial_overview" -> "আর্থিক সংক্ষিপ্ত বিবরণ"
        "payment_reminder" -> "পেমেন্ট অনুস্মারক"
        "no_valid_emi_due_date" -> "কোনো বৈধ EMI নির্ধারিত তারিখ নেই"
        "emi_was_due_on" -> "EMI {date}-এ নির্ধারিত ছিল"
        "emi_due_today" -> "EMI আজ পরিশোধযোগ্য"
        "reminder_due_in" -> "অনুস্মারক: {lender}-এর EMI {days} দিনের মধ্যে পরিশোধযোগ্য"
        "reminder_will_appear" -> "নির্ধারিত তারিখের {days} দিন আগে অনুস্মারক দেখা যাবে"
        "due" -> "পরিশোধযোগ্য"
        "remind_me" -> "আমাকে মনে করিয়ে দিন"
        "test_notification" -> "পরীক্ষামূলক বিজ্ঞপ্তি"

        "everyday_expenses" -> "দৈনন্দিন খরচ"
        "track_daily_spending" -> "আপনার দৈনন্দিন খরচ ট্র্যাক করুন, বিশ্লেষণ করুন এবং আরও সঞ্চয় করুন।"
        "add_expense" -> "খরচ যোগ করুন"
        "expense_category" -> "খরচের বিভাগ"
        "select_category" -> "বিভাগ নির্বাচন করুন"
        "amount" -> "পরিমাণ"
        "enter_amount" -> "পরিমাণ লিখুন"
        "note" -> "নোট"
        "enter_note" -> "নোট লিখুন"
        "note_optional" -> "নোট (ঐচ্ছিক)"
        "date" -> "তারিখ"

        "spending_limit" -> "খরচের সীমা"
        "spending_limits" -> "খরচের সীমা"
        "set_spending_limit" -> "খরচের সীমা নির্ধারণ করুন"
        "save_expense" -> "খরচ সংরক্ষণ করুন"
        "update_expense" -> "খরচ আপডেট করুন"
        "delete_expense" -> "খরচ মুছে দিন"
        "edit_expense" -> "খরচ সম্পাদনা করুন"

        "total_expenses" -> "মোট খরচ"
        "total_spent" -> "মোট খরচ"
        "remaining_limit" -> "অবশিষ্ট সীমা"
        "expense_breakdown" -> "খরচের বিভাজন"
        "expense_summary" -> "খরচের সারাংশ"
        "no_expenses" -> "এখনও কোনো খরচ যোগ করা হয়নি।"

        "expense_added" -> "খরচ সফলভাবে যোগ হয়েছে।"
        "expense_updated" -> "খরচ সফলভাবে আপডেট হয়েছে।"
        "expense_deleted" -> "খরচ সফলভাবে মুছে ফেলা হয়েছে।"

        "invalid_amount" -> "অবৈধ পরিমাণ।"
        "amount_required" -> "পরিমাণ প্রয়োজন।"
        "category_required" -> "বিভাগ প্রয়োজন।"
        "category" -> "বিভাগ"
        "recent_expenses" -> "সাম্প্রতিক খরচ"

        "current_limit" -> "বর্তমান সীমা"
        "set_limit" -> "সীমা নির্ধারণ করুন"
        "limit_amount" -> "সীমার পরিমাণ"

        "food" -> "খাবার"
        "travel" -> "ভ্রমণ"
        "shopping" -> "কেনাকাটা"
        "bills" -> "বিল"
        "entertainment" -> "বিনোদন"
        "other" -> "অন্যান্য"

        "spending_limit_set" -> "খরচের সীমা সফলভাবে নির্ধারণ করা হয়েছে।"
        "current_spending" -> "বর্তমান খরচ"
        "limit" -> "সীমা"
        "remaining" -> "অবশিষ্ট"

        "smart_alert" -> "স্মার্ট সতর্কতা"
        "smart_alerts" -> "স্মার্ট সতর্কতা"
        "spending_alerts" -> "খরচের সতর্কতা"
        "high_spending_alert" -> "বেশি খরচের সতর্কতা"
        "category_limit_alert" -> "বিভাগের সীমা সতর্কতা"
        "budget_alert" -> "বাজেট সতর্কতা"
        "high_spending" -> "বেশি খরচ"
        "approaching_limit" -> "সীমার কাছাকাছি"

        "financial_health_tips" -> "আর্থিক স্বাস্থ্য টিপস"
        "financial_tips" -> "আর্থিক টিপস"
        "tip" -> "টিপ"
        "tips" -> "টিপস"
        "save_money_tip" -> "দৈনন্দিন খরচ পর্যবেক্ষণ করে অর্থ সঞ্চয় করুন।"
        "track_expenses_tip" -> "নিয়মিত আপনার খরচ ট্র্যাক করুন।"
        "avoid_unnecessary_spending_tip" -> "অপ্রয়োজনীয় খরচ এড়িয়ে চলুন।"
        "build_emergency_fund" -> "জরুরি তহবিল তৈরি করুন"
        "monitor_recurring" -> "নিয়মিত খরচ পর্যবেক্ষণ করুন"
        "avoid_new_emi" -> "অতিরিক্ত নতুন EMI এড়িয়ে চলুন"
        "set_savings_goal" -> "সঞ্চয়ের লক্ষ্য নির্ধারণ করুন"

        "monthly_insights" -> "মাসিক অন্তর্দৃষ্টি"
        "monthly_overview" -> "মাসিক সংক্ষিপ্ত বিবরণ"
        "monthly_summary" -> "মাসিক সারাংশ"
        "select_month" -> "মাস নির্বাচন করুন"
        "this_month" -> "এই মাস"
        "previous_month" -> "আগের মাস"
        "next_month" -> "পরের মাস"
        "previous_months" -> "আগের মাসগুলো"
        "current_month" -> "বর্তমান মাস"
        "total_monthly_spending" -> "মোট মাসিক খরচ"
        "monthly_expenses" -> "মাসিক খরচ"
        "total_spent_this_month" -> "এই মাসে মোট খরচ"
        "category_breakdown" -> "বিভাগ অনুযায়ী বিভাজন"
        "highest_spending_category" -> "সর্বাধিক খরচের বিভাগ"
        "lowest_spending_category" -> "সর্বনিম্ন খরচের বিভাগ"
        "average_daily_spending" -> "গড় দৈনিক খরচ"
        "monthly_savings" -> "মাসিক সঞ্চয়"
        "spending_summary" -> "খরচের সারাংশ"
        "no_monthly_insights" -> "কোনো মাসিক অন্তর্দৃষ্টি পাওয়া যায়নি।"
        "monthly_insight_message" -> "এখানে আপনার মাসিক খরচের সংক্ষিপ্ত বিবরণ রয়েছে।"
        "no_data_for_month" -> "এই মাসের জন্য কোনো তথ্য নেই।"
        "monthly_total" -> "মাসিক মোট"
        "monthly_average" -> "মাসিক গড়"
        "daily_average" -> "দৈনিক গড়"
        "top_category" -> "শীর্ষ বিভাগ"
        "highest_expense" -> "সর্বোচ্চ খরচ"
        "lowest_expense" -> "সর্বনিম্ন খরচ"
        "expense_trend" -> "খরচের প্রবণতা"
        "spending_pattern" -> "খরচের ধরন"
        "compare_months" -> "মাসের তুলনা করুন"
        "monthly_report" -> "মাসিক রিপোর্ট"
        "summary" -> "সারাংশ"

        "smart_financial_decisions" -> "স্মার্ট আর্থিক সিদ্ধান্ত"
        "make_smarter_decisions" -> "আপনার আর্থিক তথ্য ব্যবহার করে আরও ভালো সিদ্ধান্ত নিন।"
        "what_should_i_cut" -> "আমার কী কমানো উচিত?"
        "what_if" -> "যদি এমন হয়?"
        "see_reducing_spending" -> "খরচ কমালে আপনার উপলব্ধ অর্থের উপর কী প্রভাব পড়বে তা দেখুন।"
        "current_available_money" -> "বর্তমানে উপলব্ধ অর্থ"
        "available_money" -> "উপলব্ধ অর্থ"
        "available_after_suggested_cuts" -> "প্রস্তাবিত খরচ কমানোর পরে উপলব্ধ অর্থ"
        "smart_suggestions" -> "স্মার্ট পরামর্শ"
        "top_suggestions" -> "প্রধান পরামর্শ"
        "suggestions" -> "পরামর্শ"
        "recommendations" -> "সুপারিশ"
        "suggested_action" -> "প্রস্তাবিত পদক্ষেপ"
        "potential_savings" -> "সম্ভাব্য সঞ্চয়"
        "potential_monthly_savings" -> "সম্ভাব্য মাসিক সঞ্চয়"
        "potential_monthly_savings_zero" -> "সম্ভাব্য মাসিক সঞ্চয়: {amount}"

        "add_expenses_personalized" -> "ব্যক্তিগত পরামর্শ পেতে প্রথমে কিছু খরচ যোগ করুন।"
        "no_major_reduction" -> "আপনার বর্তমান খরচে বড় কোনো কমানোর সুযোগ দেখা যাচ্ছে না।"
        "suggestions_current_spending" -> "পরামর্শ আপনার বর্তমান খরচের ধরন অনুযায়ী দেওয়া হয়েছে।"

        "reduce_spending" -> "খরচ কমান"
        "reduce_category" -> "বিভাগের খরচ কমান"
        "save_more" -> "আরও সঞ্চয় করুন"
        "spending_habit" -> "খরচের অভ্যাস"
        "financial_decision" -> "আর্থিক সিদ্ধান্ত"

        "cut_shopping" -> "কেনাকাটা কমান"
        "cut_food" -> "খাবারের খরচ কমান"
        "cut_entertainment" -> "বিনোদনের খরচ কমান"
        "cut_travel" -> "ভ্রমণের খরচ কমান"
        "cut_bills" -> "বিলের খরচ কমান"
        "cut_other" -> "অন্যান্য খরচ কমান"

        "simple_actions_financial_situation" -> "আপনার আর্থিক অবস্থার ভিত্তিতে সহজ পদক্ষেপ।"
        "set_aside_available_money" -> "প্রতি মাসে আপনার উপলব্ধ অর্থের একটি অংশ আলাদা করে রাখুন।"
        "review_subscriptions" -> "নিয়মিত সাবস্ক্রিপশন এবং পুনরাবৃত্ত খরচ পর্যালোচনা করুন।"
        "check_existing_emi" -> "আরেকটি EMI যোগ করার আগে আপনার বর্তমান EMI দায়বদ্ধতা পরীক্ষা করুন।"
        "use_savings_goal" -> "সম্ভাব্য মাসিক সঞ্চয়কে প্রাথমিক লক্ষ্য হিসেবে ব্যবহার করুন।"
        "set_monthly_limits" -> "আপনার খরচের বিভাগগুলোর জন্য মাসিক সীমা নির্ধারণ করুন।"
        "spending_alerts_actual" -> "আপনার প্রকৃত খরচের ভিত্তিতে খরচের সতর্কতা।"
        "latest_entries" -> "আপনার সাম্প্রতিক এন্ট্রিগুলো।"

        "cut" -> "কমান"
        "suggested" -> "প্রস্তাবিত"
        "spent" -> "খরচ"
        "keep_it_up" -> "এভাবেই চালিয়ে যান!"
        "view_summary" -> "সারাংশ দেখুন"
        "apply_suggestion" -> "পরামর্শ প্রয়োগ করুন"

        "date_not_set" -> "তারিখ নির্ধারণ করা হয়নি"
        "enter_valid_amount" -> "একটি বৈধ পরিমাণ লিখুন।"
        "enter_valid_limit" -> "একটি বৈধ সীমা লিখুন।"

        "where_did_you_spend" -> "আপনি কোথায় খরচ করেছেন?"
        "expense_note" -> "খরচের নোট"
        "today" -> "আজ"
        "yesterday" -> "গতকাল"

        "back" -> "পিছনে"

        else -> englishText(key)
    }
}


// ============================================================
// TAMIL
// ============================================================

fun tamilText(key: String): String {

    return when (key) {

        "paywise" -> "PayWise"
        "tagline" -> "புத்திசாலித்தனமாக செலவு செய்யுங்கள், புத்திசாலித்தனமாக சேமியுங்கள்."

        "login" -> "உள்நுழைவு"
        "signup" -> "புதிய கணக்கை உருவாக்கவும்"
        "create_account" -> "கணக்கை உருவாக்கவும்"
        "back_to_login" -> "உள்நுழைவுக்கு திரும்பவும்"
        "create_your_account" -> "உங்கள் கணக்கை உருவாக்கவும்"

        "full_name" -> "முழு பெயர்"
        "email" -> "மின்னஞ்சல் ஐடி"
        "password" -> "கடவுச்சொல்"
        "confirm_password" -> "கடவுச்சொல்லை உறுதிப்படுத்தவும்"
        "mobile" -> "மொபைல் எண்"

        "dashboard" -> "டாஷ்போர்டு"
        "add_salary" -> "சம்பளத்தைச் சேர்க்கவும்"
        "my_emis" -> "எனது EMIகள்"
        "add_emi" -> "EMI சேர்க்கவும்"
        "financial_summary" -> "நிதிச் சுருக்கம்"
        "profile" -> "சுயவிவரம்"
        "settings" -> "அமைப்புகள்"
        "logout" -> "வெளியேறு"

        "welcome" -> "வரவேற்கிறோம்"
        "manage_finances" -> "உங்கள் நிதியை புத்திசாலித்தனமாக நிர்வகிக்கவும்."
        "loading_financial_data" -> "நிதித் தகவல் ஏற்றப்படுகிறது..."

        "monthly_salary" -> "மாத சம்பளம்"
        "total_emi" -> "மொத்த EMI"
        "remaining_salary" -> "மீதமுள்ள சம்பளம்"
        "emi_ratio" -> "EMI விகிதம்"
        "financial_status" -> "நிதி நிலை"
        "your_emis" -> "உங்கள் EMIகள்"

        "no_emi_records" -> "EMI பதிவுகள் எதுவும் இல்லை."
        "add_update_salary" -> "சம்பளத்தைச் சேர்க்க / புதுப்பிக்கவும்"
        "save_salary" -> "சம்பளத்தைச் சேமிக்கவும்"
        "enter_valid_salary" -> "சரியான சம்பளத்தை உள்ளிடவும்."
        "salary_updated" -> "சம்பளம் வெற்றிகரமாக புதுப்பிக்கப்பட்டது."

        "bank_lender" -> "வங்கி / கடன் வழங்குநர்"
        "emi_amount" -> "EMI தொகை"
        "due_date" -> "கட்டண தேதி"
        "frequency" -> "அதிர்வெண்"
        "monthly" -> "மாதாந்திர"

        "valid_emi" -> "சரியான EMI விவரங்களை உள்ளிடவும்."
        "emi_added" -> "EMI வெற்றிகரமாக சேர்க்கப்பட்டது."
        "no_financial_data" -> "நிதித் தகவல் இல்லை."

        "my_profile" -> "எனது சுயவிவரம்"
        "profile_information" -> "சுயவிவரத் தகவல்"
        "personal_information" -> "தனிப்பட்ட தகவல்"
        "not_available" -> "கிடைக்கவில்லை"

        "notifications" -> "அறிவிப்புகள்"
        "emi_due_reminders" -> "EMI கட்டண நினைவூட்டல்கள்"
        "payment_reminders" -> "கட்டண நினைவூட்டல்கள்"
        "reminder_days" -> "நினைவூட்டல் நாட்கள்"
        "days" -> "நாட்கள்"

        "financial_preferences" -> "நிதி விருப்பங்கள்"
        "currency" -> "நாணயம்"
        "primary_currency" -> "முதன்மை நாணயம்"
        "show_financial_status" -> "நிதி நிலையை காண்பிக்கவும்"

        "language" -> "மொழி"
        "app_language" -> "ஆப் மொழி"
        "region" -> "பிராந்தியம்"
        "country_region" -> "நாடு / பிராந்தியம்"

        "about_paywise" -> "PayWise பற்றி"
        "version" -> "பதிப்பு 1.0"

        "select_currency" -> "நாணயத்தைத் தேர்ந்தெடுக்கவும்"
        "search_currency" -> "நாணயம் அல்லது குறியீட்டைத் தேடவும்"
        "select_country" -> "நாடு / பிராந்தியத்தைத் தேர்ந்தெடுக்கவும்"
        "search_country" -> "நாட்டைத் தேடவும்"
        "select_language" -> "மொழியைத் தேர்ந்தெடுக்கவும்"
        "search_language" -> "மொழியைத் தேடவும்"

        "no_results" -> "முடிவுகள் எதுவும் இல்லை"
        "close" -> "மூடு"

        "please_fill_all" -> "அனைத்து புலங்களையும் நிரப்பவும்."
        "passwords_not_match" -> "கடவுச்சொற்கள் பொருந்தவில்லை."
        "please_enter_login" -> "மின்னஞ்சல் மற்றும் கடவுச்சொல்லை உள்ளிடவும்."

        "profile_photo" -> "சுயவிவரப் படம்"
        "select_photo" -> "படத்தைத் தேர்ந்தெடுக்கவும்"
        "edit_profile" -> "சுயவிவரத்தைத் திருத்தவும்"
        "financial_profile" -> "நிதி சுயவிவரம்"
        "total_active_emis" -> "மொத்த செயலில் உள்ள EMIகள்"
        "total_monthly_emi" -> "மொத்த மாத EMI பொறுப்பு"

        "account_information" -> "கணக்குத் தகவல்"
        "account_creation_date" -> "கணக்கு உருவாக்கிய தேதி"
        "user_id" -> "பயனர் ஐடி"

        "security" -> "பாதுகாப்பு"
        "change_password" -> "கடவுச்சொல்லை மாற்றவும்"
        "current_password" -> "தற்போதைய கடவுச்சொல்"
        "new_password" -> "புதிய கடவுச்சொல்"
        "save_changes" -> "மாற்றங்களைச் சேமிக்கவும்"
        "cancel" -> "ரத்து செய்"

        "financial_overview" -> "நிதி மேலோட்டம்"
        "payment_reminder" -> "கட்டண நினைவூட்டல்"
        "no_valid_emi_due_date" -> "சரியான EMI கட்டண தேதி இல்லை"
        "emi_was_due_on" -> "EMI {date} அன்று செலுத்த வேண்டியது"
        "emi_due_today" -> "EMI இன்று செலுத்த வேண்டும்"
        "reminder_due_in" -> "நினைவூட்டல்: {lender} EMI {days} நாட்களில் செலுத்த வேண்டும்"
        "reminder_will_appear" -> "கட்டண தேதிக்கு {days} நாட்களுக்கு முன் நினைவூட்டல் தோன்றும்"
        "due" -> "செலுத்த வேண்டியது"
        "remind_me" -> "எனக்கு நினைவூட்டவும்"
        "test_notification" -> "சோதனை அறிவிப்பு"

        "everyday_expenses" -> "தினசரி செலவுகள்"
        "track_daily_spending" -> "உங்கள் தினசரி செலவுகளை கண்காணித்து, பகுப்பாய்வு செய்து, அதிகமாக சேமிக்கவும்."
        "add_expense" -> "செலவைச் சேர்க்கவும்"
        "expense_category" -> "செலவு வகை"
        "select_category" -> "வகையைத் தேர்ந்தெடுக்கவும்"
        "amount" -> "தொகை"
        "enter_amount" -> "தொகையை உள்ளிடவும்"
        "note" -> "குறிப்பு"
        "enter_note" -> "குறிப்பை உள்ளிடவும்"
        "note_optional" -> "குறிப்பு (விருப்பத்தேர்வு)"
        "date" -> "தேதி"

        "spending_limit" -> "செலவு வரம்பு"
        "spending_limits" -> "செலவு வரம்புகள்"
        "set_spending_limit" -> "செலவு வரம்பை அமைக்கவும்"
        "save_expense" -> "செலவைச் சேமிக்கவும்"
        "update_expense" -> "செலவைப் புதுப்பிக்கவும்"
        "delete_expense" -> "செலவை நீக்கவும்"
        "edit_expense" -> "செலவைத் திருத்தவும்"

        "total_expenses" -> "மொத்த செலவுகள்"
        "total_spent" -> "மொத்த செலவு"
        "remaining_limit" -> "மீதமுள்ள வரம்பு"
        "expense_breakdown" -> "செலவு விவரம்"
        "expense_summary" -> "செலவு சுருக்கம்"
        "no_expenses" -> "இதுவரை செலவுகள் சேர்க்கப்படவில்லை."

        "expense_added" -> "செலவு வெற்றிகரமாக சேர்க்கப்பட்டது."
        "expense_updated" -> "செலவு வெற்றிகரமாக புதுப்பிக்கப்பட்டது."
        "expense_deleted" -> "செலவு வெற்றிகரமாக நீக்கப்பட்டது."

        "invalid_amount" -> "தவறான தொகை."
        "amount_required" -> "தொகை தேவை."
        "category_required" -> "வகை தேவை."
        "category" -> "வகை"
        "recent_expenses" -> "சமீபத்திய செலவுகள்"

        "current_limit" -> "தற்போதைய வரம்பு"
        "set_limit" -> "வரம்பை அமைக்கவும்"
        "limit_amount" -> "வரம்புத் தொகை"

        "food" -> "உணவு"
        "travel" -> "பயணம்"
        "shopping" -> "ஷாப்பிங்"
        "bills" -> "பில்கள்"
        "entertainment" -> "பொழுதுபோக்கு"
        "other" -> "மற்றவை"

        "spending_limit_set" -> "செலவு வரம்பு வெற்றிகரமாக அமைக்கப்பட்டது."
        "current_spending" -> "தற்போதைய செலவு"
        "limit" -> "வரம்பு"
        "remaining" -> "மீதம்"

        "smart_alert" -> "ஸ்மார்ட் எச்சரிக்கை"
        "smart_alerts" -> "ஸ்மார்ட் எச்சரிக்கைகள்"
        "spending_alerts" -> "செலவு எச்சரிக்கைகள்"
        "high_spending_alert" -> "அதிக செலவு எச்சரிக்கை"
        "category_limit_alert" -> "வகை வரம்பு எச்சரிக்கை"
        "budget_alert" -> "பட்ஜெட் எச்சரிக்கை"
        "high_spending" -> "அதிக செலவு"
        "approaching_limit" -> "வரம்பை நெருங்குகிறது"

        "financial_health_tips" -> "நிதி ஆரோக்கிய குறிப்புகள்"
        "financial_tips" -> "நிதி குறிப்புகள்"
        "tip" -> "குறிப்பு"
        "tips" -> "குறிப்புகள்"
        "save_money_tip" -> "தினசரி செலவுகளை கண்காணிப்பதன் மூலம் பணத்தை சேமிக்கவும்."
        "track_expenses_tip" -> "உங்கள் செலவுகளை தொடர்ந்து கண்காணிக்கவும்."
        "avoid_unnecessary_spending_tip" -> "தேவையற்ற செலவுகளைத் தவிர்க்கவும்."
        "build_emergency_fund" -> "அவசரகால நிதியை உருவாக்கவும்"
        "monitor_recurring" -> "தொடர்ச்சியான செலவுகளை கண்காணிக்கவும்"
        "avoid_new_emi" -> "அதிகப்படியான புதிய EMIகளைத் தவிர்க்கவும்"
        "set_savings_goal" -> "சேமிப்பு இலக்கை அமைக்கவும்"

        "monthly_insights" -> "மாதாந்திர நுண்ணறிவுகள்"
        "monthly_overview" -> "மாதாந்திர மேலோட்டம்"
        "monthly_summary" -> "மாதாந்திர சுருக்கம்"
        "select_month" -> "மாதத்தைத் தேர்ந்தெடுக்கவும்"
        "this_month" -> "இந்த மாதம்"
        "previous_month" -> "முந்தைய மாதம்"
        "next_month" -> "அடுத்த மாதம்"
        "previous_months" -> "முந்தைய மாதங்கள்"
        "current_month" -> "தற்போதைய மாதம்"
        "total_monthly_spending" -> "மொத்த மாத செலவு"
        "monthly_expenses" -> "மாத செலவுகள்"
        "total_spent_this_month" -> "இந்த மாத மொத்த செலவு"
        "category_breakdown" -> "வகை வாரியான விவரம்"
        "highest_spending_category" -> "அதிக செலவு செய்யப்பட்ட வகை"
        "lowest_spending_category" -> "குறைந்த செலவு செய்யப்பட்ட வகை"
        "average_daily_spending" -> "சராசரி தினசரி செலவு"
        "monthly_savings" -> "மாத சேமிப்பு"
        "spending_summary" -> "செலவு சுருக்கம்"
        "no_monthly_insights" -> "மாதாந்திர நுண்ணறிவுகள் இல்லை."
        "monthly_insight_message" -> "உங்கள் மாத செலவுகளின் மேலோட்டம் இங்கே உள்ளது."
        "no_data_for_month" -> "இந்த மாதத்திற்கு தரவு இல்லை."
        "monthly_total" -> "மாத மொத்தம்"
        "monthly_average" -> "மாத சராசரி"
        "daily_average" -> "தினசரி சராசரி"
        "top_category" -> "முக்கிய வகை"
        "highest_expense" -> "அதிக செலவு"
        "lowest_expense" -> "குறைந்த செலவு"
        "expense_trend" -> "செலவு போக்கு"
        "spending_pattern" -> "செலவு முறை"
        "compare_months" -> "மாதங்களை ஒப்பிடவும்"
        "monthly_report" -> "மாதாந்திர அறிக்கை"
        "summary" -> "சுருக்கம்"

        "smart_financial_decisions" -> "ஸ்மார்ட் நிதி முடிவுகள்"
        "make_smarter_decisions" -> "உங்கள் நிதித் தரவைப் பயன்படுத்தி சிறந்த முடிவுகளை எடுக்கவும்."
        "what_should_i_cut" -> "நான் எதை குறைக்க வேண்டும்?"
        "what_if" -> "இப்படி இருந்தால்?"
        "see_reducing_spending" -> "செலவை குறைப்பது உங்கள் கிடைக்கும் பணத்தை எவ்வாறு பாதிக்கும் என்பதைப் பார்க்கவும்."
        "current_available_money" -> "தற்போதைய கிடைக்கும் பணம்"
        "available_money" -> "கிடைக்கும் பணம்"
        "available_after_suggested_cuts" -> "பரிந்துரைக்கப்பட்ட குறைப்புகளுக்குப் பிறகு கிடைக்கும் பணம்"
        "smart_suggestions" -> "ஸ்மார்ட் பரிந்துரைகள்"
        "top_suggestions" -> "முக்கிய பரிந்துரைகள்"
        "suggestions" -> "பரிந்துரைகள்"
        "recommendations" -> "பரிந்துரைகள்"
        "suggested_action" -> "பரிந்துரைக்கப்பட்ட செயல்"
        "potential_savings" -> "சாத்தியமான சேமிப்பு"
        "potential_monthly_savings" -> "சாத்தியமான மாத சேமிப்பு"
        "potential_monthly_savings_zero" -> "சாத்தியமான மாத சேமிப்பு: {amount}"

        "add_expenses_personalized" -> "தனிப்பயன் பரிந்துரைகளைப் பெற முதலில் சில செலவுகளைச் சேர்க்கவும்."
        "no_major_reduction" -> "உங்கள் தற்போதைய செலவுகளில் பெரிய குறைப்பு வாய்ப்பு இல்லை."
        "suggestions_current_spending" -> "பரிந்துரைகள் உங்கள் தற்போதைய செலவு முறையை அடிப்படையாகக் கொண்டவை."

        "reduce_spending" -> "செலவை குறைக்கவும்"
        "reduce_category" -> "வகை செலவை குறைக்கவும்"
        "save_more" -> "மேலும் சேமிக்கவும்"
        "spending_habit" -> "செலவு பழக்கம்"
        "financial_decision" -> "நிதி முடிவு"

        "cut_shopping" -> "ஷாப்பிங் செலவை குறைக்கவும்"
        "cut_food" -> "உணவு செலவை குறைக்கவும்"
        "cut_entertainment" -> "பொழுதுபோக்கு செலவை குறைக்கவும்"
        "cut_travel" -> "பயண செலவை குறைக்கவும்"
        "cut_bills" -> "பில் செலவை குறைக்கவும்"
        "cut_other" -> "மற்ற செலவுகளை குறைக்கவும்"

        "simple_actions_financial_situation" -> "உங்கள் நிதி நிலையை அடிப்படையாகக் கொண்ட எளிய செயல்கள்."
        "set_aside_available_money" -> "ஒவ்வொரு மாதமும் உங்கள் கிடைக்கும் பணத்தின் ஒரு பகுதியை ஒதுக்கி வைக்கவும்."
        "review_subscriptions" -> "சந்தாக்கள் மற்றும் தொடர்ச்சியான செலவுகளை தொடர்ந்து மதிப்பாய்வு செய்யவும்."
        "check_existing_emi" -> "மற்றொரு EMI சேர்ப்பதற்கு முன் உங்கள் தற்போதைய EMI பொறுப்பைச் சரிபார்க்கவும்."
        "use_savings_goal" -> "சாத்தியமான மாத சேமிப்பை ஆரம்ப சேமிப்பு இலக்காகப் பயன்படுத்தவும்."
        "set_monthly_limits" -> "உங்கள் செலவு வகைகளுக்கு மாத வரம்புகளை அமைக்கவும்."
        "spending_alerts_actual" -> "உங்கள் உண்மையான செலவுகளை அடிப்படையாகக் கொண்ட செலவு எச்சரிக்கைகள்."
        "latest_entries" -> "உங்கள் சமீபத்திய பதிவுகள்."

        "cut" -> "குறைக்கவும்"
        "suggested" -> "பரிந்துரைக்கப்பட்டது"
        "spent" -> "செலவிட்டது"
        "keep_it_up" -> "தொடர்ந்து சிறப்பாக செய்யுங்கள்!"
        "view_summary" -> "சுருக்கத்தைப் பார்க்கவும்"
        "apply_suggestion" -> "பரிந்துரையைப் பயன்படுத்தவும்"

        "date_not_set" -> "தேதி அமைக்கப்படவில்லை"
        "enter_valid_amount" -> "சரியான தொகையை உள்ளிடவும்."
        "enter_valid_limit" -> "சரியான வரம்பை உள்ளிடவும்."

        "where_did_you_spend" -> "எங்கு செலவு செய்தீர்கள்?"
        "expense_note" -> "செலவு குறிப்பு"
        "today" -> "இன்று"
        "yesterday" -> "நேற்று"

        "back" -> "பின்செல்"

        else -> englishText(key)
    }
}


// ============================================================
// TELUGU
// ============================================================

fun teluguText(key: String): String {

    return when (key) {

        "paywise" -> "PayWise"
        "tagline" -> "తెలివిగా ఖర్చు చేయండి, తెలివిగా పొదుపు చేయండి."

        "login" -> "లాగిన్"
        "signup" -> "కొత్త ఖాతాను సృష్టించండి"
        "create_account" -> "ఖాతాను సృష్టించండి"
        "back_to_login" -> "లాగిన్‌కు తిరిగి వెళ్ళండి"
        "create_your_account" -> "మీ ఖాతాను సృష్టించండి"

        "full_name" -> "పూర్తి పేరు"
        "email" -> "ఈమెయిల్ ఐడి"
        "password" -> "పాస్‌వర్డ్"
        "confirm_password" -> "పాస్‌వర్డ్‌ను నిర్ధారించండి"
        "mobile" -> "మొబైల్ నంబర్"

        "dashboard" -> "డ్యాష్‌బోర్డ్"
        "add_salary" -> "జీతం జోడించండి"
        "my_emis" -> "నా EMIలు"
        "add_emi" -> "EMI జోడించండి"
        "financial_summary" -> "ఆర్థిక సారాంశం"
        "profile" -> "ప్రొఫైల్"
        "settings" -> "సెట్టింగ్‌లు"
        "logout" -> "లాగ్ అవుట్"

        "welcome" -> "స్వాగతం"
        "manage_finances" -> "మీ ఆర్థిక వ్యవహారాలను తెలివిగా నిర్వహించండి."
        "loading_financial_data" -> "ఆర్థిక సమాచారం లోడ్ అవుతోంది..."

        "monthly_salary" -> "నెలవారీ జీతం"
        "total_emi" -> "మొత్తం EMI"
        "remaining_salary" -> "మిగిలిన జీతం"
        "emi_ratio" -> "EMI నిష్పత్తి"
        "financial_status" -> "ఆర్థిక స్థితి"
        "your_emis" -> "మీ EMIలు"

        "no_emi_records" -> "EMI రికార్డులు ఏవీ లేవు."
        "add_update_salary" -> "జీతం జోడించండి / నవీకరించండి"
        "save_salary" -> "జీతాన్ని సేవ్ చేయండి"
        "enter_valid_salary" -> "చెల్లుబాటు అయ్యే జీతాన్ని నమోదు చేయండి."
        "salary_updated" -> "జీతం విజయవంతంగా నవీకరించబడింది."

        "bank_lender" -> "బ్యాంక్ / రుణదాత"
        "emi_amount" -> "EMI మొత్తం"
        "due_date" -> "గడువు తేదీ"
        "frequency" -> "పౌనఃపున్యం"
        "monthly" -> "నెలవారీ"

        "valid_emi" -> "దయచేసి సరైన EMI వివరాలను నమోదు చేయండి."
        "emi_added" -> "EMI విజయవంతంగా జోడించబడింది."
        "no_financial_data" -> "ఆర్థిక సమాచారం అందుబాటులో లేదు."

        "my_profile" -> "నా ప్రొఫైల్"
        "profile_information" -> "ప్రొఫైల్ సమాచారం"
        "personal_information" -> "వ్యక్తిగత సమాచారం"
        "not_available" -> "అందుబాటులో లేదు"

        "notifications" -> "నోటిఫికేషన్‌లు"
        "emi_due_reminders" -> "EMI గడువు రిమైండర్‌లు"
        "payment_reminders" -> "చెల్లింపు రిమైండర్‌లు"
        "reminder_days" -> "రిమైండర్ రోజులు"
        "days" -> "రోజులు"

        "financial_preferences" -> "ఆర్థిక ప్రాధాన్యతలు"
        "currency" -> "కరెన్సీ"
        "primary_currency" -> "ప్రధాన కరెన్సీ"
        "show_financial_status" -> "ఆర్థిక స్థితిని చూపించండి"

        "language" -> "భాష"
        "app_language" -> "యాప్ భాష"
        "region" -> "ప్రాంతం"
        "country_region" -> "దేశం / ప్రాంతం"

        "about_paywise" -> "PayWise గురించి"
        "version" -> "వెర్షన్ 1.0"

        "select_currency" -> "కరెన్సీని ఎంచుకోండి"
        "search_currency" -> "కరెన్సీ లేదా కోడ్‌ను శోధించండి"
        "select_country" -> "దేశం / ప్రాంతాన్ని ఎంచుకోండి"
        "search_country" -> "దేశాన్ని శోధించండి"
        "select_language" -> "భాషను ఎంచుకోండి"
        "search_language" -> "భాషను శోధించండి"

        "no_results" -> "ఫలితాలు ఏవీ లేవు"
        "close" -> "మూసివేయండి"

        "please_fill_all" -> "దయచేసి అన్ని ఫీల్డ్‌లను పూరించండి."
        "passwords_not_match" -> "పాస్‌వర్డ్‌లు సరిపోలలేదు."
        "please_enter_login" -> "దయచేసి ఈమెయిల్ మరియు పాస్‌వర్డ్ నమోదు చేయండి."

        "profile_photo" -> "ప్రొఫైల్ ఫోటో"
        "select_photo" -> "ఫోటోను ఎంచుకోండి"
        "edit_profile" -> "ప్రొఫైల్‌ను సవరించండి"
        "financial_profile" -> "ఆర్థిక ప్రొఫైల్"
        "total_active_emis" -> "మొత్తం యాక్టివ్ EMIలు"
        "total_monthly_emi" -> "మొత్తం నెలవారీ EMI బాధ్యత"

        "account_information" -> "ఖాతా సమాచారం"
        "account_creation_date" -> "ఖాతా సృష్టించిన తేదీ"
        "user_id" -> "యూజర్ ఐడి"

        "security" -> "భద్రత"
        "change_password" -> "పాస్‌వర్డ్ మార్చండి"
        "current_password" -> "ప్రస్తుత పాస్‌వర్డ్"
        "new_password" -> "కొత్త పాస్‌వర్డ్"
        "save_changes" -> "మార్పులను సేవ్ చేయండి"
        "cancel" -> "రద్దు చేయండి"

        "financial_overview" -> "ఆర్థిక అవలోకనం"
        "payment_reminder" -> "చెల్లింపు రిమైండర్"
        "no_valid_emi_due_date" -> "చెల్లుబాటు అయ్యే EMI గడువు తేదీ అందుబాటులో లేదు"
        "emi_was_due_on" -> "EMI {date}న చెల్లించాల్సి ఉంది"
        "emi_due_today" -> "EMI ఈరోజు చెల్లించాలి"
        "reminder_due_in" -> "రిమైండర్: {lender} EMI {days} రోజుల్లో చెల్లించాలి"
        "reminder_will_appear" -> "గడువు తేదీకి {days} రోజుల ముందు రిమైండర్ కనిపిస్తుంది"
        "due" -> "చెల్లించాల్సినది"
        "remind_me" -> "నాకు గుర్తు చేయండి"
        "test_notification" -> "టెస్ట్ నోటిఫికేషన్"

        "everyday_expenses" -> "రోజువారీ ఖర్చులు"
        "track_daily_spending" -> "మీ రోజువారీ ఖర్చులను ట్రాక్ చేసి, విశ్లేషించి, మరింత పొదుపు చేయండి."
        "add_expense" -> "ఖర్చును జోడించండి"
        "expense_category" -> "ఖర్చు వర్గం"
        "select_category" -> "వర్గాన్ని ఎంచుకోండి"
        "amount" -> "మొత్తం"
        "enter_amount" -> "మొత్తాన్ని నమోదు చేయండి"
        "note" -> "గమనిక"
        "enter_note" -> "గమనికను నమోదు చేయండి"
        "note_optional" -> "గమనిక (ఐచ్ఛికం)"
        "date" -> "తేదీ"

        "spending_limit" -> "ఖర్చు పరిమితి"
        "spending_limits" -> "ఖర్చు పరిమితులు"
        "set_spending_limit" -> "ఖర్చు పరిమితిని సెట్ చేయండి"
        "save_expense" -> "ఖర్చును సేవ్ చేయండి"
        "update_expense" -> "ఖర్చును నవీకరించండి"
        "delete_expense" -> "ఖర్చును తొలగించండి"
        "edit_expense" -> "ఖర్చును సవరించండి"

        "total_expenses" -> "మొత్తం ఖర్చులు"
        "total_spent" -> "మొత్తం ఖర్చు"
        "remaining_limit" -> "మిగిలిన పరిమితి"
        "expense_breakdown" -> "ఖర్చుల విభజన"
        "expense_summary" -> "ఖర్చుల సారాంశం"
        "no_expenses" -> "ఇంకా ఖర్చులు జోడించలేదు."

        "expense_added" -> "ఖర్చు విజయవంతంగా జోడించబడింది."
        "expense_updated" -> "ఖర్చు విజయవంతంగా నవీకరించబడింది."
        "expense_deleted" -> "ఖర్చు విజయవంతంగా తొలగించబడింది."

        "invalid_amount" -> "చెల్లని మొత్తం."
        "amount_required" -> "మొత్తం అవసరం."
        "category_required" -> "వర్గం అవసరం."
        "category" -> "వర్గం"
        "recent_expenses" -> "ఇటీవలి ఖర్చులు"

        "current_limit" -> "ప్రస్తుత పరిమితి"
        "set_limit" -> "పరిమితిని సెట్ చేయండి"
        "limit_amount" -> "పరిమితి మొత్తం"

        "food" -> "ఆహారం"
        "travel" -> "ప్రయాణం"
        "shopping" -> "షాపింగ్"
        "bills" -> "బిల్లులు"
        "entertainment" -> "వినోదం"
        "other" -> "ఇతర"

        "spending_limit_set" -> "ఖర్చు పరిమితి విజయవంతంగా సెట్ చేయబడింది."
        "current_spending" -> "ప్రస్తుత ఖర్చు"
        "limit" -> "పరిమితి"
        "remaining" -> "మిగిలినది"

        "smart_alert" -> "స్మార్ట్ అలర్ట్"
        "smart_alerts" -> "స్మార్ట్ అలర్ట్‌లు"
        "spending_alerts" -> "ఖర్చు అలర్ట్‌లు"
        "high_spending_alert" -> "అధిక ఖర్చు అలర్ట్"
        "category_limit_alert" -> "వర్గ పరిమితి అలర్ట్"
        "budget_alert" -> "బడ్జెట్ అలర్ట్"
        "high_spending" -> "అధిక ఖర్చు"
        "approaching_limit" -> "పరిమితికి చేరువలో ఉంది"

        "financial_health_tips" -> "ఆర్థిక ఆరోగ్య చిట్కాలు"
        "financial_tips" -> "ఆర్థిక చిట్కాలు"
        "tip" -> "చిట్కా"
        "tips" -> "చిట్కాలు"
        "save_money_tip" -> "రోజువారీ ఖర్చులను పర్యవేక్షించడం ద్వారా డబ్బు ఆదా చేయండి."
        "track_expenses_tip" -> "మీ ఖర్చులను క్రమం తప్పకుండా ట్రాక్ చేయండి."
        "avoid_unnecessary_spending_tip" -> "అనవసర ఖర్చులను నివారించండి."
        "build_emergency_fund" -> "అత్యవసర నిధిని నిర్మించండి"
        "monitor_recurring" -> "పునరావృత ఖర్చులను పర్యవేక్షించండి"
        "avoid_new_emi" -> "అధిక కొత్త EMIలను నివారించండి"
        "set_savings_goal" -> "పొదుపు లక్ష్యాన్ని సెట్ చేయండి"

        "monthly_insights" -> "నెలవారీ అంతర్దృష్టులు"
        "monthly_overview" -> "నెలవారీ అవలోకనం"
        "monthly_summary" -> "నెలవారీ సారాంశం"
        "select_month" -> "నెలను ఎంచుకోండి"
        "this_month" -> "ఈ నెల"
        "previous_month" -> "గత నెల"
        "next_month" -> "తదుపరి నెల"
        "previous_months" -> "గత నెలలు"
        "current_month" -> "ప్రస్తుత నెల"
        "total_monthly_spending" -> "మొత్తం నెలవారీ ఖర్చు"
        "monthly_expenses" -> "నెలవారీ ఖర్చులు"
        "total_spent_this_month" -> "ఈ నెల మొత్తం ఖర్చు"
        "category_breakdown" -> "వర్గాల వారీగా విభజన"
        "highest_spending_category" -> "అధిక ఖర్చు వర్గం"
        "lowest_spending_category" -> "తక్కువ ఖర్చు వర్గం"
        "average_daily_spending" -> "సగటు రోజువారీ ఖర్చు"
        "monthly_savings" -> "నెలవారీ పొదుపు"
        "spending_summary" -> "ఖర్చుల సారాంశం"
        "no_monthly_insights" -> "నెలవారీ అంతర్దృష్టులు అందుబాటులో లేవు."
        "monthly_insight_message" -> "మీ నెలవారీ ఖర్చుల అవలోకనం ఇక్కడ ఉంది."
        "no_data_for_month" -> "ఈ నెలకు డేటా అందుబాటులో లేదు."
        "monthly_total" -> "నెలవారీ మొత్తం"
        "monthly_average" -> "నెలవారీ సగటు"
        "daily_average" -> "రోజువారీ సగటు"
        "top_category" -> "అగ్ర వర్గం"
        "highest_expense" -> "అధిక ఖర్చు"
        "lowest_expense" -> "తక్కువ ఖర్చు"
        "expense_trend" -> "ఖర్చు ధోరణి"
        "spending_pattern" -> "ఖర్చు విధానం"
        "compare_months" -> "నెలలను పోల్చండి"
        "monthly_report" -> "నెలవారీ నివేదిక"
        "summary" -> "సారాంశం"

        "smart_financial_decisions" -> "స్మార్ట్ ఆర్థిక నిర్ణయాలు"
        "make_smarter_decisions" -> "మీ ఆర్థిక డేటాను ఉపయోగించి మెరుగైన నిర్ణయాలు తీసుకోండి."
        "what_should_i_cut" -> "నేను ఏమి తగ్గించాలి?"
        "what_if" -> "ఇలా చేస్తే?"
        "see_reducing_spending" -> "ఖర్చు తగ్గించడం మీ అందుబాటులో ఉన్న డబ్బును ఎలా ప్రభావితం చేస్తుందో చూడండి."
        "current_available_money" -> "ప్రస్తుతం అందుబాటులో ఉన్న డబ్బు"
        "available_money" -> "అందుబాటులో ఉన్న డబ్బు"
        "available_after_suggested_cuts" -> "సూచించిన తగ్గింపుల తర్వాత అందుబాటులో ఉన్న డబ్బు"
        "smart_suggestions" -> "స్మార్ట్ సూచనలు"
        "top_suggestions" -> "ముఖ్య సూచనలు"
        "suggestions" -> "సూచనలు"
        "recommendations" -> "సిఫార్సులు"
        "suggested_action" -> "సూచించిన చర్య"
        "potential_savings" -> "సంభావ్య పొదుపు"
        "potential_monthly_savings" -> "సంభావ్య నెలవారీ పొదుపు"
        "potential_monthly_savings_zero" -> "సంభావ్య నెలవారీ పొదుపు: {amount}"

        "add_expenses_personalized" -> "వ్యక్తిగత సూచనలు పొందడానికి ముందుగా కొన్ని ఖర్చులను జోడించండి."
        "no_major_reduction" -> "మీ ప్రస్తుత ఖర్చుల్లో పెద్ద తగ్గింపు అవకాశం కనిపించడం లేదు."
        "suggestions_current_spending" -> "సూచనలు మీ ప్రస్తుత ఖర్చు విధానంపై ఆధారపడి ఉంటాయి."

        "reduce_spending" -> "ఖర్చును తగ్గించండి"
        "reduce_category" -> "వర్గ ఖర్చును తగ్గించండి"
        "save_more" -> "మరింత పొదుపు చేయండి"
        "spending_habit" -> "ఖర్చు అలవాటు"
        "financial_decision" -> "ఆర్థిక నిర్ణయం"

        "cut_shopping" -> "షాపింగ్ ఖర్చును తగ్గించండి"
        "cut_food" -> "ఆహార ఖర్చును తగ్గించండి"
        "cut_entertainment" -> "వినోద ఖర్చును తగ్గించండి"
        "cut_travel" -> "ప్రయాణ ఖర్చును తగ్గించండి"
        "cut_bills" -> "బిల్లుల ఖర్చును తగ్గించండి"
        "cut_other" -> "ఇతర ఖర్చులను తగ్గించండి"

        "simple_actions_financial_situation" -> "మీ ఆర్థిక పరిస్థితి ఆధారంగా సులభమైన చర్యలు."
        "set_aside_available_money" -> "ప్రతి నెల మీ అందుబాటులో ఉన్న డబ్బులో కొంత భాగాన్ని పక్కన పెట్టండి."
        "review_subscriptions" -> "సబ్‌స్క్రిప్షన్‌లు మరియు పునరావృత ఖర్చులను క్రమం తప్పకుండా సమీక్షించండి."
        "check_existing_emi" -> "మరో EMI జోడించే ముందు మీ ప్రస్తుత EMI బాధ్యతను తనిఖీ చేయండి."
        "use_savings_goal" -> "మీ సంభావ్య నెలవారీ పొదుపును ప్రారంభ లక్ష్యంగా ఉపయోగించండి."
        "set_monthly_limits" -> "మీ ఖర్చు వర్గాలకు నెలవారీ పరిమితులను సెట్ చేయండి."
        "spending_alerts_actual" -> "మీ వాస్తవ ఖర్చుల ఆధారంగా ఖర్చు అలర్ట్‌లు."
        "latest_entries" -> "మీ తాజా ఎంట్రీలు."

        "cut" -> "తగ్గించండి"
        "suggested" -> "సూచించబడింది"
        "spent" -> "ఖర్చు"
        "keep_it_up" -> "ఇలాగే కొనసాగించండి!"
        "view_summary" -> "సారాంశాన్ని చూడండి"
        "apply_suggestion" -> "సూచనను వర్తింపజేయండి"

        "date_not_set" -> "తేదీ సెట్ చేయలేదు"
        "enter_valid_amount" -> "చెల్లుబాటు అయ్యే మొత్తాన్ని నమోదు చేయండి."
        "enter_valid_limit" -> "చెల్లుబాటు అయ్యే పరిమితిని నమోదు చేయండి."

        "where_did_you_spend" -> "మీరు ఎక్కడ ఖర్చు చేశారు?"
        "expense_note" -> "ఖర్చు గమనిక"
        "today" -> "ఈరోజు"
        "yesterday" -> "నిన్న"

        "back" -> "వెనుకకు"

        else -> englishText(key)
    }
}


// ============================================================
// CHINESE
// ============================================================

fun zhText(key: String): String {

    return when (key) {

        "paywise" -> "PayWise"
        "tagline" -> "聪明消费，智慧生活。"

        "login" -> "登录"
        "signup" -> "创建新账户"
        "create_account" -> "创建账户"
        "back_to_login" -> "返回登录"
        "create_your_account" -> "创建您的账户"

        "full_name" -> "姓名"
        "email" -> "电子邮箱"
        "password" -> "密码"
        "confirm_password" -> "确认密码"
        "mobile" -> "手机号码"

        "dashboard" -> "仪表板"
        "add_salary" -> "添加工资"
        "my_emis" -> "我的 EMI"
        "add_emi" -> "添加 EMI"
        "financial_summary" -> "财务摘要"
        "profile" -> "个人资料"
        "settings" -> "设置"
        "logout" -> "退出登录"

        "welcome" -> "欢迎"
        "manage_finances" -> "明智地管理您的财务。"
        "loading_financial_data" -> "正在加载财务数据..."

        "monthly_salary" -> "月薪"
        "total_emi" -> "EMI总额"
        "remaining_salary" -> "剩余工资"
        "emi_ratio" -> "EMI比例"
        "financial_status" -> "财务状况"
        "your_emis" -> "您的 EMI"

        "no_emi_records" -> "未找到 EMI 记录。"
        "add_update_salary" -> "添加 / 更新工资"
        "save_salary" -> "保存工资"
        "enter_valid_salary" -> "请输入有效工资。"
        "salary_updated" -> "工资更新成功。"

        "bank_lender" -> "银行 / 贷款机构"
        "emi_amount" -> "EMI金额"
        "due_date" -> "到期日"
        "frequency" -> "频率"
        "monthly" -> "每月"

        "valid_emi" -> "请输入有效的 EMI 信息。"
        "emi_added" -> "EMI 添加成功。"
        "no_financial_data" -> "没有可用的财务数据。"

        "my_profile" -> "我的资料"
        "profile_information" -> "个人资料信息"
        "personal_information" -> "个人信息"
        "not_available" -> "不可用"

        "notifications" -> "通知"
        "emi_due_reminders" -> "EMI 到期提醒"
        "payment_reminders" -> "付款提醒"
        "reminder_days" -> "提醒天数"
        "days" -> "天"

        "financial_preferences" -> "财务偏好"
        "currency" -> "货币"
        "primary_currency" -> "主要货币"
        "show_financial_status" -> "显示财务状况"

        "language" -> "语言"
        "app_language" -> "应用语言"
        "region" -> "地区"
        "country_region" -> "国家 / 地区"

        "about_paywise" -> "关于 PayWise"
        "version" -> "版本 1.0"

        "select_currency" -> "选择货币"
        "search_currency" -> "搜索货币或代码"
        "select_country" -> "选择国家 / 地区"
        "search_country" -> "搜索国家"
        "select_language" -> "选择语言"
        "search_language" -> "搜索语言"

        "no_results" -> "未找到结果"
        "close" -> "关闭"

        "please_fill_all" -> "请填写所有字段。"
        "passwords_not_match" -> "密码不匹配。"
        "please_enter_login" -> "请输入电子邮件和密码。"

        "profile_photo" -> "头像"
        "select_photo" -> "选择照片"
        "edit_profile" -> "编辑个人资料"
        "financial_profile" -> "财务资料"
        "total_active_emis" -> "活跃 EMI 总数"
        "total_monthly_emi" -> "每月 EMI 总承诺额"

        "account_information" -> "账户信息"
        "account_creation_date" -> "账户创建日期"
        "user_id" -> "用户 ID"

        "security" -> "安全"
        "change_password" -> "更改密码"
        "current_password" -> "当前密码"
        "new_password" -> "新密码"
        "save_changes" -> "保存更改"
        "cancel" -> "取消"

        "financial_overview" -> "财务概览"
        "payment_reminder" -> "付款提醒"
        "no_valid_emi_due_date" -> "没有可用的有效 EMI 到期日"
        "emi_was_due_on" -> "EMI 原定于 {date} 到期"
        "emi_due_today" -> "EMI 今天到期"
        "reminder_due_in" -> "提醒：{lender} 的 EMI 将在 {days} 天后到期"
        "reminder_will_appear" -> "提醒将在到期日前 {days} 天显示"
        "due" -> "到期"
        "remind_me" -> "提醒我"
        "test_notification" -> "测试通知"

        "everyday_expenses" -> "日常支出"
        "track_daily_spending" -> "跟踪和分析您的日常支出，并节省更多。"
        "add_expense" -> "添加支出"
        "expense_category" -> "支出类别"
        "select_category" -> "选择类别"
        "amount" -> "金额"
        "enter_amount" -> "输入金额"
        "note" -> "备注"
        "enter_note" -> "输入备注"
        "note_optional" -> "备注（可选）"
        "date" -> "日期"

        "spending_limit" -> "支出限额"
        "spending_limits" -> "支出限额"
        "set_spending_limit" -> "设置支出限额"
        "save_expense" -> "保存支出"
        "update_expense" -> "更新支出"
        "delete_expense" -> "删除支出"
        "edit_expense" -> "编辑支出"

        "total_expenses" -> "总支出"
        "total_spent" -> "总花费"
        "remaining_limit" -> "剩余限额"
        "expense_breakdown" -> "支出明细"
        "expense_summary" -> "支出摘要"
        "no_expenses" -> "尚未添加任何支出。"

        "expense_added" -> "支出添加成功。"
        "expense_updated" -> "支出更新成功。"
        "expense_deleted" -> "支出删除成功。"

        "invalid_amount" -> "金额无效。"
        "amount_required" -> "请输入金额。"
        "category_required" -> "请选择类别。"
        "category" -> "类别"
        "recent_expenses" -> "最近支出"

        "current_limit" -> "当前限额"
        "set_limit" -> "设置限额"
        "limit_amount" -> "限额金额"

        "food" -> "食品"
        "travel" -> "旅行"
        "shopping" -> "购物"
        "bills" -> "账单"
        "entertainment" -> "娱乐"
        "other" -> "其他"

        "spending_limit_set" -> "支出限额设置成功。"
        "current_spending" -> "当前支出"
        "limit" -> "限额"
        "remaining" -> "剩余"

        "smart_alert" -> "智能提醒"
        "smart_alerts" -> "智能提醒"
        "spending_alerts" -> "支出提醒"
        "high_spending_alert" -> "高支出提醒"
        "category_limit_alert" -> "类别限额提醒"
        "budget_alert" -> "预算提醒"
        "high_spending" -> "支出较高"
        "approaching_limit" -> "接近限额"

        "financial_health_tips" -> "财务健康建议"
        "financial_tips" -> "财务建议"
        "tip" -> "建议"
        "tips" -> "建议"
        "save_money_tip" -> "通过监控日常支出来节省资金。"
        "track_expenses_tip" -> "定期跟踪您的支出。"
        "avoid_unnecessary_spending_tip" -> "避免不必要的支出。"
        "build_emergency_fund" -> "建立应急基金"
        "monitor_recurring" -> "监控经常性支出"
        "avoid_new_emi" -> "避免过多的新 EMI"
        "set_savings_goal" -> "设定储蓄目标"

        "monthly_insights" -> "月度洞察"
        "monthly_overview" -> "月度概览"
        "monthly_summary" -> "月度摘要"
        "select_month" -> "选择月份"
        "this_month" -> "本月"
        "previous_month" -> "上个月"
        "next_month" -> "下个月"
        "previous_months" -> "之前的月份"
        "current_month" -> "当前月份"
        "total_monthly_spending" -> "每月总支出"
        "monthly_expenses" -> "月度支出"
        "total_spent_this_month" -> "本月总支出"
        "category_breakdown" -> "类别明细"
        "highest_spending_category" -> "支出最高类别"
        "lowest_spending_category" -> "支出最低类别"
        "average_daily_spending" -> "平均每日支出"
        "monthly_savings" -> "月度储蓄"
        "spending_summary" -> "支出摘要"
        "no_monthly_insights" -> "没有可用的月度洞察。"
        "monthly_insight_message" -> "这是您的月度支出概览。"
        "no_data_for_month" -> "本月没有可用数据。"
        "monthly_total" -> "月度总额"
        "monthly_average" -> "月度平均"
        "daily_average" -> "每日平均"
        "top_category" -> "主要类别"
        "highest_expense" -> "最高支出"
        "lowest_expense" -> "最低支出"
        "expense_trend" -> "支出趋势"
        "spending_pattern" -> "消费模式"
        "compare_months" -> "比较月份"
        "monthly_report" -> "月度报告"
        "summary" -> "摘要"

        "smart_financial_decisions" -> "智能财务决策"
        "make_smarter_decisions" -> "使用您的财务数据做出更明智的决策。"
        "what_should_i_cut" -> "我应该减少什么？"
        "what_if" -> "如果这样呢？"
        "see_reducing_spending" -> "查看减少支出对可用资金的影响。"
        "current_available_money" -> "当前可用资金"
        "available_money" -> "可用资金"
        "available_after_suggested_cuts" -> "建议削减后的可用资金"
        "smart_suggestions" -> "智能建议"
        "top_suggestions" -> "主要建议"
        "suggestions" -> "建议"
        "recommendations" -> "推荐"
        "suggested_action" -> "建议行动"
        "potential_savings" -> "潜在储蓄"
        "potential_monthly_savings" -> "预计每月可节省"
        "potential_monthly_savings_zero" -> "预计每月可节省：{amount}"

        "add_expenses_personalized" -> "请先添加一些支出，以获取个性化建议。"
        "no_major_reduction" -> "您当前的支出没有明显的削减机会。"
        "suggestions_current_spending" -> "建议基于您当前的消费模式。"

        "reduce_spending" -> "减少支出"
        "reduce_category" -> "减少类别支出"
        "save_more" -> "节省更多"
        "spending_habit" -> "消费习惯"
        "financial_decision" -> "财务决策"

        "cut_shopping" -> "减少购物支出"
        "cut_food" -> "减少食品支出"
        "cut_entertainment" -> "减少娱乐支出"
        "cut_travel" -> "减少旅行支出"
        "cut_bills" -> "减少账单支出"
        "cut_other" -> "减少其他支出"

        "simple_actions_financial_situation" -> "根据您的财务状况提供简单的行动建议。"
        "set_aside_available_money" -> "尝试每月留出一部分可用资金。"
        "review_subscriptions" -> "定期检查订阅和经常性支出。"
        "check_existing_emi" -> "增加新的 EMI 之前，请检查现有的 EMI 负担。"
        "use_savings_goal" -> "将预计每月节省金额作为初始储蓄目标。"
        "set_monthly_limits" -> "为各支出类别设置每月限额。"
        "spending_alerts_actual" -> "根据您的实际支出提供消费提醒。"
        "latest_entries" -> "您的最新记录。"

        "cut" -> "减少"
        "suggested" -> "建议"
        "spent" -> "已花费"
        "keep_it_up" -> "继续保持！"
        "view_summary" -> "查看摘要"
        "apply_suggestion" -> "应用建议"

        "date_not_set" -> "未设置日期"
        "enter_valid_amount" -> "请输入有效金额。"
        "enter_valid_limit" -> "请输入有效限额。"

        "where_did_you_spend" -> "您在哪里消费了？"
        "expense_note" -> "支出备注"
        "today" -> "今天"
        "yesterday" -> "昨天"

        "back" -> "返回"

        else -> englishText(key)
    }
}


// ============================================================
// ARABIC
// ============================================================

fun arText(key: String): String {

    return when (key) {

        "paywise" -> "PayWise"
        "tagline" -> "أنفق بذكاء، وعِش بحكمة."

        "login" -> "تسجيل الدخول"
        "signup" -> "إنشاء حساب جديد"
        "create_account" -> "إنشاء حساب"
        "back_to_login" -> "العودة إلى تسجيل الدخول"
        "create_your_account" -> "أنشئ حسابك"

        "full_name" -> "الاسم الكامل"
        "email" -> "البريد الإلكتروني"
        "password" -> "كلمة المرور"
        "confirm_password" -> "تأكيد كلمة المرور"
        "mobile" -> "رقم الهاتف"

        "dashboard" -> "لوحة التحكم"
        "add_salary" -> "إضافة الراتب"
        "my_emis" -> "أقساطي"
        "add_emi" -> "إضافة قسط"
        "financial_summary" -> "الملخص المالي"
        "profile" -> "الملف الشخصي"
        "settings" -> "الإعدادات"
        "logout" -> "تسجيل الخروج"

        "welcome" -> "مرحباً"
        "manage_finances" -> "أدر أموالك بحكمة."
        "loading_financial_data" -> "جارٍ تحميل البيانات المالية..."

        "monthly_salary" -> "الراتب الشهري"
        "total_emi" -> "إجمالي الأقساط"
        "remaining_salary" -> "الراتب المتبقي"
        "emi_ratio" -> "نسبة الأقساط"
        "financial_status" -> "الحالة المالية"
        "your_emis" -> "أقساطك"

        "no_emi_records" -> "لم يتم العثور على سجلات أقساط."
        "add_update_salary" -> "إضافة / تحديث الراتب"
        "save_salary" -> "حفظ الراتب"
        "enter_valid_salary" -> "أدخل راتباً صالحاً."
        "salary_updated" -> "تم تحديث الراتب بنجاح."

        "bank_lender" -> "البنك / المقرض"
        "emi_amount" -> "مبلغ القسط"
        "due_date" -> "تاريخ الاستحقاق"
        "frequency" -> "التكرار"
        "monthly" -> "شهري"

        "valid_emi" -> "يرجى إدخال تفاصيل قسط صالحة."
        "emi_added" -> "تمت إضافة القسط بنجاح."
        "no_financial_data" -> "لا توجد بيانات مالية متاحة."

        "my_profile" -> "ملفي الشخصي"
        "profile_information" -> "معلومات الملف الشخصي"
        "personal_information" -> "المعلومات الشخصية"
        "not_available" -> "غير متاح"

        "notifications" -> "الإشعارات"
        "emi_due_reminders" -> "تذكيرات استحقاق الأقساط"
        "payment_reminders" -> "تذكيرات الدفع"
        "reminder_days" -> "أيام التذكير"
        "days" -> "أيام"

        "financial_preferences" -> "التفضيلات المالية"
        "currency" -> "العملة"
        "primary_currency" -> "العملة الأساسية"
        "show_financial_status" -> "إظهار الحالة المالية"

        "language" -> "اللغة"
        "app_language" -> "لغة التطبيق"
        "region" -> "المنطقة"
        "country_region" -> "الدولة / المنطقة"

        "about_paywise" -> "حول PayWise"
        "version" -> "الإصدار 1.0"

        "select_currency" -> "اختر العملة"
        "search_currency" -> "البحث عن العملة أو الرمز"
        "select_country" -> "اختر الدولة / المنطقة"
        "search_country" -> "البحث عن دولة"
        "select_language" -> "اختر اللغة"
        "search_language" -> "البحث عن لغة"

        "no_results" -> "لم يتم العثور على نتائج"
        "close" -> "إغلاق"

        "please_fill_all" -> "يرجى ملء جميع الحقول."
        "passwords_not_match" -> "كلمتا المرور غير متطابقتين."
        "please_enter_login" -> "يرجى إدخال البريد الإلكتروني وكلمة المرور."

        "profile_photo" -> "صورة الملف الشخصي"
        "select_photo" -> "اختر صورة"
        "edit_profile" -> "تعديل الملف الشخصي"
        "financial_profile" -> "الملف المالي"
        "total_active_emis" -> "إجمالي الأقساط النشطة"
        "total_monthly_emi" -> "إجمالي الالتزام الشهري بالأقساط"

        "account_information" -> "معلومات الحساب"
        "account_creation_date" -> "تاريخ إنشاء الحساب"
        "user_id" -> "معرف المستخدم"

        "security" -> "الأمان"
        "change_password" -> "تغيير كلمة المرور"
        "current_password" -> "كلمة المرور الحالية"
        "new_password" -> "كلمة المرور الجديدة"
        "save_changes" -> "حفظ التغييرات"
        "cancel" -> "إلغاء"

        "financial_overview" -> "نظرة عامة مالية"
        "payment_reminder" -> "تذكير بالدفع"
        "no_valid_emi_due_date" -> "لا يوجد تاريخ استحقاق صالح للقسط"
        "emi_was_due_on" -> "كان القسط مستحقاً في {date}"
        "emi_due_today" -> "القسط مستحق اليوم"
        "reminder_due_in" -> "تذكير: قسط {lender} مستحق خلال {days} أيام"
        "reminder_will_appear" -> "سيظهر التذكير قبل تاريخ الاستحقاق بـ {days} أيام"
        "due" -> "مستحق"
        "remind_me" -> "ذكرني"
        "test_notification" -> "اختبار الإشعار"

        "everyday_expenses" -> "المصروفات اليومية"
        "track_daily_spending" -> "تتبع مصروفاتك اليومية وحللها وادخر المزيد."
        "add_expense" -> "إضافة مصروف"
        "expense_category" -> "فئة المصروف"
        "select_category" -> "اختر الفئة"
        "amount" -> "المبلغ"
        "enter_amount" -> "أدخل المبلغ"
        "note" -> "ملاحظة"
        "enter_note" -> "أدخل ملاحظة"
        "note_optional" -> "ملاحظة (اختياري)"
        "date" -> "التاريخ"

        "spending_limit" -> "حد الإنفاق"
        "spending_limits" -> "حدود الإنفاق"
        "set_spending_limit" -> "تعيين حد الإنفاق"
        "save_expense" -> "حفظ المصروف"
        "update_expense" -> "تحديث المصروف"
        "delete_expense" -> "حذف المصروف"
        "edit_expense" -> "تعديل المصروف"

        "total_expenses" -> "إجمالي المصروفات"
        "total_spent" -> "إجمالي الإنفاق"
        "remaining_limit" -> "الحد المتبقي"
        "expense_breakdown" -> "تفاصيل المصروفات"
        "expense_summary" -> "ملخص المصروفات"
        "no_expenses" -> "لم تتم إضافة أي مصروفات بعد."

        "expense_added" -> "تمت إضافة المصروف بنجاح."
        "expense_updated" -> "تم تحديث المصروف بنجاح."
        "expense_deleted" -> "تم حذف المصروف بنجاح."

        "invalid_amount" -> "مبلغ غير صالح."
        "amount_required" -> "المبلغ مطلوب."
        "category_required" -> "الفئة مطلوبة."
        "category" -> "الفئة"
        "recent_expenses" -> "المصروفات الأخيرة"

        "current_limit" -> "الحد الحالي"
        "set_limit" -> "تعيين الحد"
        "limit_amount" -> "مبلغ الحد"

        "food" -> "الطعام"
        "travel" -> "السفر"
        "shopping" -> "التسوق"
        "bills" -> "الفواتير"
        "entertainment" -> "الترفيه"
        "other" -> "أخرى"

        "spending_limit_set" -> "تم تعيين حد الإنفاق بنجاح."
        "current_spending" -> "الإنفاق الحالي"
        "limit" -> "الحد"
        "remaining" -> "المتبقي"

        "smart_alert" -> "تنبيه ذكي"
        "smart_alerts" -> "تنبيهات ذكية"
        "spending_alerts" -> "تنبيهات الإنفاق"
        "high_spending_alert" -> "تنبيه الإنفاق المرتفع"
        "category_limit_alert" -> "تنبيه حد الفئة"
        "budget_alert" -> "تنبيه الميزانية"
        "high_spending" -> "إنفاق مرتفع"
        "approaching_limit" -> "الاقتراب من الحد"

        "financial_health_tips" -> "نصائح للصحة المالية"
        "financial_tips" -> "نصائح مالية"
        "tip" -> "نصيحة"
        "tips" -> "نصائح"
        "save_money_tip" -> "وفر المال من خلال مراقبة مصروفاتك اليومية."
        "track_expenses_tip" -> "تتبع مصروفاتك بانتظام."
        "avoid_unnecessary_spending_tip" -> "تجنب الإنفاق غير الضروري."
        "build_emergency_fund" -> "أنشئ صندوق طوارئ"
        "monitor_recurring" -> "راقب المصروفات المتكررة"
        "avoid_new_emi" -> "تجنب الأقساط الجديدة المفرطة"
        "set_savings_goal" -> "حدد هدفاً للادخار"

        "monthly_insights" -> "الرؤى الشهرية"
        "monthly_overview" -> "النظرة الشهرية"
        "monthly_summary" -> "الملخص الشهري"
        "select_month" -> "اختر الشهر"
        "this_month" -> "هذا الشهر"
        "previous_month" -> "الشهر السابق"
        "next_month" -> "الشهر التالي"
        "previous_months" -> "الأشهر السابقة"
        "current_month" -> "الشهر الحالي"
        "total_monthly_spending" -> "إجمالي الإنفاق الشهري"
        "monthly_expenses" -> "المصروفات الشهرية"
        "total_spent_this_month" -> "إجمالي الإنفاق هذا الشهر"
        "category_breakdown" -> "التفصيل حسب الفئة"
        "highest_spending_category" -> "الفئة الأعلى إنفاقاً"
        "lowest_spending_category" -> "الفئة الأقل إنفاقاً"
        "average_daily_spending" -> "متوسط الإنفاق اليومي"
        "monthly_savings" -> "الادخار الشهري"
        "spending_summary" -> "ملخص الإنفاق"
        "no_monthly_insights" -> "لا توجد رؤى شهرية متاحة."
        "monthly_insight_message" -> "إليك نظرة عامة على إنفاقك الشهري."
        "no_data_for_month" -> "لا توجد بيانات لهذا الشهر."
        "monthly_total" -> "الإجمالي الشهري"
        "monthly_average" -> "المتوسط الشهري"
        "daily_average" -> "المتوسط اليومي"
        "top_category" -> "الفئة الرئيسية"
        "highest_expense" -> "أعلى مصروف"
        "lowest_expense" -> "أقل مصروف"
        "expense_trend" -> "اتجاه الإنفاق"
        "spending_pattern" -> "نمط الإنفاق"
        "compare_months" -> "مقارنة الأشهر"
        "monthly_report" -> "التقرير الشهري"
        "summary" -> "الملخص"

        "smart_financial_decisions" -> "قرارات مالية ذكية"
        "make_smarter_decisions" -> "اتخذ قرارات أكثر ذكاءً باستخدام بياناتك المالية."
        "what_should_i_cut" -> "ما الذي يجب أن أقلله؟"
        "what_if" -> "ماذا لو؟"
        "see_reducing_spending" -> "شاهد كيف يمكن أن يؤثر تقليل الإنفاق على أموالك المتاحة."
        "current_available_money" -> "الأموال المتاحة حالياً"
        "available_money" -> "الأموال المتاحة"
        "available_after_suggested_cuts" -> "الأموال المتاحة بعد التخفيضات المقترحة"
        "smart_suggestions" -> "اقتراحات ذكية"
        "top_suggestions" -> "أهم الاقتراحات"
        "suggestions" -> "الاقتراحات"
        "recommendations" -> "التوصيات"
        "suggested_action" -> "الإجراء المقترح"
        "potential_savings" -> "الادخار المحتمل"
        "potential_monthly_savings" -> "الادخار الشهري المحتمل"
        "potential_monthly_savings_zero" -> "الادخار الشهري المحتمل: {amount}"

        "add_expenses_personalized" -> "أضف بعض المصروفات أولاً للحصول على اقتراحات مخصصة."
        "no_major_reduction" -> "لا يظهر إنفاقك الحالي فرصة كبيرة للتخفيض."
        "suggestions_current_spending" -> "تعتمد الاقتراحات على نمط إنفاقك الحالي."

        "reduce_spending" -> "تقليل الإنفاق"
        "reduce_category" -> "تقليل إنفاق الفئة"
        "save_more" -> "ادخر المزيد"
        "spending_habit" -> "عادات الإنفاق"
        "financial_decision" -> "قرار مالي"

        "cut_shopping" -> "قلل الإنفاق على التسوق"
        "cut_food" -> "قلل الإنفاق على الطعام"
        "cut_entertainment" -> "قلل الإنفاق على الترفيه"
        "cut_travel" -> "قلل الإنفاق على السفر"
        "cut_bills" -> "قلل إنفاق الفواتير"
        "cut_other" -> "قلل المصروفات الأخرى"

        "simple_actions_financial_situation" -> "إجراءات بسيطة بناءً على وضعك المالي."
        "set_aside_available_money" -> "حاول تخصيص جزء من أموالك المتاحة كل شهر."
        "review_subscriptions" -> "راجع الاشتراكات والمصروفات المتكررة بانتظام."
        "check_existing_emi" -> "تحقق من التزامات الأقساط الحالية قبل إضافة قسط آخر."
        "use_savings_goal" -> "استخدم مدخراتك الشهرية المحتملة كهدف أولي."
        "set_monthly_limits" -> "حدد حدوداً شهرية لفئات الإنفاق."
        "spending_alerts_actual" -> "تنبيهات إنفاق بناءً على مصروفاتك الفعلية."
        "latest_entries" -> "أحدث إدخالاتك."

        "cut" -> "تقليل"
        "suggested" -> "مقترح"
        "spent" -> "تم إنفاقه"
        "keep_it_up" -> "استمر على هذا النحو!"
        "view_summary" -> "عرض الملخص"
        "apply_suggestion" -> "تطبيق الاقتراح"

        "date_not_set" -> "لم يتم تحديد التاريخ"
        "enter_valid_amount" -> "أدخل مبلغاً صالحاً."
        "enter_valid_limit" -> "أدخل حداً صالحاً."

        "where_did_you_spend" -> "أين أنفقت؟"
        "expense_note" -> "ملاحظة المصروف"
        "today" -> "اليوم"
        "yesterday" -> "أمس"

        "back" -> "رجوع"

        else -> englishText(key)
    }
}


// ============================================================
// KOREAN
// ============================================================

fun koText(key: String): String {

    return when (key) {

        "paywise" -> "PayWise"
        "tagline" -> "현명하게 쓰고, 현명하게 절약하세요."

        "login" -> "로그인"
        "signup" -> "새 계정 만들기"
        "create_account" -> "계정 만들기"
        "back_to_login" -> "로그인으로 돌아가기"
        "create_your_account" -> "계정을 만들어 보세요"

        "full_name" -> "이름"
        "email" -> "이메일"
        "password" -> "비밀번호"
        "confirm_password" -> "비밀번호 확인"
        "mobile" -> "휴대폰 번호"

        "dashboard" -> "대시보드"
        "add_salary" -> "급여 추가"
        "my_emis" -> "내 EMI"
        "add_emi" -> "EMI 추가"
        "financial_summary" -> "재무 요약"
        "profile" -> "프로필"
        "settings" -> "설정"
        "logout" -> "로그아웃"

        "welcome" -> "환영합니다"
        "manage_finances" -> "재정을 현명하게 관리하세요."
        "loading_financial_data" -> "재무 데이터를 불러오는 중..."

        "monthly_salary" -> "월급"
        "total_emi" -> "총 EMI"
        "remaining_salary" -> "남은 급여"
        "emi_ratio" -> "EMI 비율"
        "financial_status" -> "재무 상태"
        "your_emis" -> "내 EMI"

        "no_emi_records" -> "EMI 기록이 없습니다."
        "add_update_salary" -> "급여 추가 / 업데이트"
        "save_salary" -> "급여 저장"
        "enter_valid_salary" -> "유효한 급여를 입력하세요."
        "salary_updated" -> "급여가 성공적으로 업데이트되었습니다."

        "bank_lender" -> "은행 / 대출 기관"
        "emi_amount" -> "EMI 금액"
        "due_date" -> "납부일"
        "frequency" -> "주기"
        "monthly" -> "매월"

        "valid_emi" -> "유효한 EMI 정보를 입력하세요."
        "emi_added" -> "EMI가 성공적으로 추가되었습니다."
        "no_financial_data" -> "재무 데이터를 사용할 수 없습니다."

        "my_profile" -> "내 프로필"
        "profile_information" -> "프로필 정보"
        "personal_information" -> "개인 정보"
        "not_available" -> "사용할 수 없음"

        "notifications" -> "알림"
        "emi_due_reminders" -> "EMI 납부 알림"
        "payment_reminders" -> "결제 알림"
        "reminder_days" -> "알림 일수"
        "days" -> "일"

        "financial_preferences" -> "재무 설정"
        "currency" -> "통화"
        "primary_currency" -> "기본 통화"
        "show_financial_status" -> "재무 상태 표시"

        "language" -> "언어"
        "app_language" -> "앱 언어"
        "region" -> "지역"
        "country_region" -> "국가 / 지역"

        "about_paywise" -> "PayWise 정보"
        "version" -> "버전 1.0"

        "select_currency" -> "통화 선택"
        "search_currency" -> "통화 또는 코드 검색"
        "select_country" -> "국가 / 지역 선택"
        "search_country" -> "국가 검색"
        "select_language" -> "언어 선택"
        "search_language" -> "언어 검색"

        "no_results" -> "결과가 없습니다"
        "close" -> "닫기"

        "please_fill_all" -> "모든 필드를 입력하세요."
        "passwords_not_match" -> "비밀번호가 일치하지 않습니다."
        "please_enter_login" -> "이메일과 비밀번호를 입력하세요."

        "profile_photo" -> "프로필 사진"
        "select_photo" -> "사진 선택"
        "edit_profile" -> "프로필 수정"
        "financial_profile" -> "재무 프로필"
        "total_active_emis" -> "활성 EMI 총 개수"
        "total_monthly_emi" -> "월별 총 EMI 부담"

        "account_information" -> "계정 정보"
        "account_creation_date" -> "계정 생성 날짜"
        "user_id" -> "사용자 ID"

        "security" -> "보안"
        "change_password" -> "비밀번호 변경"
        "current_password" -> "현재 비밀번호"
        "new_password" -> "새 비밀번호"
        "save_changes" -> "변경사항 저장"
        "cancel" -> "취소"

        "financial_overview" -> "재무 개요"
        "payment_reminder" -> "결제 알림"
        "no_valid_emi_due_date" -> "유효한 EMI 납부일이 없습니다"
        "emi_was_due_on" -> "EMI 납부일은 {date}였습니다"
        "emi_due_today" -> "EMI 납부일이 오늘입니다"
        "reminder_due_in" -> "알림: {lender} EMI가 {days}일 후 만료됩니다"
        "reminder_will_appear" -> "납부일 {days}일 전에 알림이 표시됩니다"
        "due" -> "납부 예정"
        "remind_me" -> "알림 설정"
        "test_notification" -> "테스트 알림"

        "everyday_expenses" -> "일상 지출"
        "track_daily_spending" -> "일일 지출을 추적하고 분석하여 더 많이 절약하세요."
        "add_expense" -> "지출 추가"
        "expense_category" -> "지출 카테고리"
        "select_category" -> "카테고리 선택"
        "amount" -> "금액"
        "enter_amount" -> "금액 입력"
        "note" -> "메모"
        "enter_note" -> "메모 입력"
        "note_optional" -> "메모 (선택사항)"
        "date" -> "날짜"

        "spending_limit" -> "지출 한도"
        "spending_limits" -> "지출 한도"
        "set_spending_limit" -> "지출 한도 설정"
        "save_expense" -> "지출 저장"
        "update_expense" -> "지출 업데이트"
        "delete_expense" -> "지출 삭제"
        "edit_expense" -> "지출 수정"

        "total_expenses" -> "총 지출"
        "total_spent" -> "총 사용 금액"
        "remaining_limit" -> "남은 한도"
        "expense_breakdown" -> "지출 내역"
        "expense_summary" -> "지출 요약"
        "no_expenses" -> "아직 추가된 지출이 없습니다."

        "expense_added" -> "지출이 성공적으로 추가되었습니다."
        "expense_updated" -> "지출이 성공적으로 업데이트되었습니다."
        "expense_deleted" -> "지출이 성공적으로 삭제되었습니다."

        "invalid_amount" -> "잘못된 금액입니다."
        "amount_required" -> "금액이 필요합니다."
        "category_required" -> "카테고리가 필요합니다."
        "category" -> "카테고리"
        "recent_expenses" -> "최근 지출"

        "current_limit" -> "현재 한도"
        "set_limit" -> "한도 설정"
        "limit_amount" -> "한도 금액"

        "food" -> "식비"
        "travel" -> "여행"
        "shopping" -> "쇼핑"
        "bills" -> "청구서"
        "entertainment" -> "엔터테인먼트"
        "other" -> "기타"

        "spending_limit_set" -> "지출 한도가 성공적으로 설정되었습니다."
        "current_spending" -> "현재 지출"
        "limit" -> "한도"
        "remaining" -> "남은 금액"

        "smart_alert" -> "스마트 알림"
        "smart_alerts" -> "스마트 알림"
        "spending_alerts" -> "지출 알림"
        "high_spending_alert" -> "높은 지출 알림"
        "category_limit_alert" -> "카테고리 한도 알림"
        "budget_alert" -> "예산 알림"
        "high_spending" -> "높은 지출"
        "approaching_limit" -> "한도에 가까워지고 있습니다"

        "financial_health_tips" -> "재무 건강 팁"
        "financial_tips" -> "재무 팁"
        "tip" -> "팁"
        "tips" -> "팁"
        "save_money_tip" -> "일일 지출을 관리하여 돈을 절약하세요."
        "track_expenses_tip" -> "지출을 정기적으로 추적하세요."
        "avoid_unnecessary_spending_tip" -> "불필요한 지출을 피하세요."
        "build_emergency_fund" -> "비상 자금을 마련하세요"
        "monitor_recurring" -> "반복 지출을 확인하세요"
        "avoid_new_emi" -> "과도한 신규 EMI를 피하세요"
        "set_savings_goal" -> "저축 목표를 설정하세요"

        "monthly_insights" -> "월간 인사이트"
        "monthly_overview" -> "월간 개요"
        "monthly_summary" -> "월간 요약"
        "select_month" -> "월 선택"
        "this_month" -> "이번 달"
        "previous_month" -> "지난 달"
        "next_month" -> "다음 달"
        "previous_months" -> "이전 달"
        "current_month" -> "현재 달"
        "total_monthly_spending" -> "월간 총 지출"
        "monthly_expenses" -> "월간 지출"
        "total_spent_this_month" -> "이번 달 총 지출"
        "category_breakdown" -> "카테고리별 내역"
        "highest_spending_category" -> "가장 많이 지출한 카테고리"
        "lowest_spending_category" -> "가장 적게 지출한 카테고리"
        "average_daily_spending" -> "일일 평균 지출"
        "monthly_savings" -> "월간 저축"
        "spending_summary" -> "지출 요약"
        "no_monthly_insights" -> "월간 인사이트가 없습니다."
        "monthly_insight_message" -> "월간 지출에 대한 개요입니다."
        "no_data_for_month" -> "이번 달에는 데이터가 없습니다."
        "monthly_total" -> "월간 총액"
        "monthly_average" -> "월간 평균"
        "daily_average" -> "일일 평균"
        "top_category" -> "주요 카테고리"
        "highest_expense" -> "최고 지출"
        "lowest_expense" -> "최저 지출"
        "expense_trend" -> "지출 추세"
        "spending_pattern" -> "지출 패턴"
        "compare_months" -> "월 비교"
        "monthly_report" -> "월간 보고서"
        "summary" -> "요약"

        "smart_financial_decisions" -> "스마트 재무 결정"
        "make_smarter_decisions" -> "재무 데이터를 활용하여 더 현명한 결정을 내리세요."
        "what_should_i_cut" -> "무엇을 줄여야 할까요?"
        "what_if" -> "만약 이렇게 한다면?"
        "see_reducing_spending" -> "지출을 줄였을 때 사용 가능한 금액에 미치는 영향을 확인하세요."
        "current_available_money" -> "현재 사용 가능한 금액"
        "available_money" -> "사용 가능한 금액"
        "available_after_suggested_cuts" -> "추천 감축 후 사용 가능한 금액"
        "smart_suggestions" -> "스마트 제안"
        "top_suggestions" -> "주요 제안"
        "suggestions" -> "제안"
        "recommendations" -> "추천"
        "suggested_action" -> "추천 행동"
        "potential_savings" -> "잠재적 절약"
        "potential_monthly_savings" -> "예상 월간 절약"
        "potential_monthly_savings_zero" -> "예상 월간 절약: {amount}"

        "add_expenses_personalized" -> "맞춤형 제안을 받으려면 먼저 지출을 추가하세요."
        "no_major_reduction" -> "현재 지출에서는 큰 절감 기회가 보이지 않습니다."
        "suggestions_current_spending" -> "제안은 현재 지출 패턴을 기반으로 합니다."

        "reduce_spending" -> "지출 줄이기"
        "reduce_category" -> "카테고리 지출 줄이기"
        "save_more" -> "더 많이 저축하기"
        "spending_habit" -> "지출 습관"
        "financial_decision" -> "재무 결정"

        "cut_shopping" -> "쇼핑 지출 줄이기"
        "cut_food" -> "식비 줄이기"
        "cut_entertainment" -> "엔터테인먼트 지출 줄이기"
        "cut_travel" -> "여행 지출 줄이기"
        "cut_bills" -> "청구서 지출 줄이기"
        "cut_other" -> "기타 지출 줄이기"

        "simple_actions_financial_situation" -> "현재 재무 상황에 따른 간단한 행동입니다."
        "set_aside_available_money" -> "매달 사용 가능한 금액의 일부를 따로 저축해 보세요."
        "review_subscriptions" -> "구독 및 반복 지출을 정기적으로 검토하세요."
        "check_existing_emi" -> "새 EMI를 추가하기 전에 기존 EMI 부담을 확인하세요."
        "use_savings_goal" -> "예상 월간 절약액을 저축 목표의 시작점으로 사용하세요."
        "set_monthly_limits" -> "각 지출 카테고리에 월간 한도를 설정하세요."
        "spending_alerts_actual" -> "실제 지출을 기반으로 한 지출 알림입니다."
        "latest_entries" -> "최근 기록입니다."

        "cut" -> "줄이기"
        "suggested" -> "추천"
        "spent" -> "지출"
        "keep_it_up" -> "계속 잘하고 있어요!"
        "view_summary" -> "요약 보기"
        "apply_suggestion" -> "제안 적용"

        "date_not_set" -> "날짜가 설정되지 않았습니다"
        "enter_valid_amount" -> "유효한 금액을 입력하세요."
        "enter_valid_limit" -> "유효한 한도를 입력하세요."

        "where_did_you_spend" -> "어디에 지출했나요?"
        "expense_note" -> "지출 메모"
        "today" -> "오늘"
        "yesterday" -> "어제"

        "back" -> "뒤로"

        else -> englishText(key)
    }
}


// ============================================================
// JAPANESE
// ============================================================

fun japaneseText(key: String): String {

    return when (key) {

        "paywise" -> "PayWise"
        "tagline" -> "賢く使って、賢く貯めよう。"

        "login" -> "ログイン"
        "signup" -> "新しいアカウントを作成"
        "create_account" -> "アカウントを作成"
        "back_to_login" -> "ログインに戻る"
        "create_your_account" -> "アカウントを作成しましょう"

        "full_name" -> "氏名"
        "email" -> "メールアドレス"
        "password" -> "パスワード"
        "confirm_password" -> "パスワードを確認"
        "mobile" -> "携帯電話番号"

        "dashboard" -> "ダッシュボード"
        "add_salary" -> "給与を追加"
        "my_emis" -> "マイEMI"
        "add_emi" -> "EMIを追加"
        "financial_summary" -> "財務概要"
        "profile" -> "プロフィール"
        "settings" -> "設定"
        "logout" -> "ログアウト"

        "welcome" -> "ようこそ"
        "manage_finances" -> "財務を賢く管理しましょう。"
        "loading_financial_data" -> "財務データを読み込んでいます..."

        "monthly_salary" -> "月給"
        "total_emi" -> "EMI合計"
        "remaining_salary" -> "残りの給与"
        "emi_ratio" -> "EMI比率"
        "financial_status" -> "財務状況"
        "your_emis" -> "あなたのEMI"

        "no_emi_records" -> "EMIの記録がありません。"
        "add_update_salary" -> "給与を追加 / 更新"
        "save_salary" -> "給与を保存"
        "enter_valid_salary" -> "有効な給与を入力してください。"
        "salary_updated" -> "給与が正常に更新されました。"

        "bank_lender" -> "銀行 / 貸し手"
        "emi_amount" -> "EMI金額"
        "due_date" -> "支払期日"
        "frequency" -> "頻度"
        "monthly" -> "毎月"

        "valid_emi" -> "有効なEMI情報を入力してください。"
        "emi_added" -> "EMIが正常に追加されました。"
        "no_financial_data" -> "財務データがありません。"

        "my_profile" -> "マイプロフィール"
        "profile_information" -> "プロフィール情報"
        "personal_information" -> "個人情報"
        "not_available" -> "利用できません"

        "notifications" -> "通知"
        "emi_due_reminders" -> "EMI支払期日リマインダー"
        "payment_reminders" -> "支払いリマインダー"
        "reminder_days" -> "リマインダー日数"
        "days" -> "日"

        "financial_preferences" -> "財務設定"
        "currency" -> "通貨"
        "primary_currency" -> "主要通貨"
        "show_financial_status" -> "財務状況を表示"

        "language" -> "言語"
        "app_language" -> "アプリの言語"
        "region" -> "地域"
        "country_region" -> "国 / 地域"

        "about_paywise" -> "PayWiseについて"
        "version" -> "バージョン1.0"

        "select_currency" -> "通貨を選択"
        "search_currency" -> "通貨またはコードを検索"
        "select_country" -> "国 / 地域を選択"
        "search_country" -> "国を検索"
        "select_language" -> "言語を選択"
        "search_language" -> "言語を検索"

        "no_results" -> "結果が見つかりません"
        "close" -> "閉じる"

        "please_fill_all" -> "すべての項目を入力してください。"
        "passwords_not_match" -> "パスワードが一致しません。"
        "please_enter_login" -> "メールアドレスとパスワードを入力してください。"

        "profile_photo" -> "プロフィール写真"
        "select_photo" -> "写真を選択"
        "edit_profile" -> "プロフィールを編集"
        "financial_profile" -> "財務プロフィール"
        "total_active_emis" -> "アクティブなEMIの合計"
        "total_monthly_emi" -> "月間EMI総額"

        "account_information" -> "アカウント情報"
        "account_creation_date" -> "アカウント作成日"
        "user_id" -> "ユーザーID"

        "security" -> "セキュリティ"
        "change_password" -> "パスワードを変更"
        "current_password" -> "現在のパスワード"
        "new_password" -> "新しいパスワード"
        "save_changes" -> "変更を保存"
        "cancel" -> "キャンセル"

        "financial_overview" -> "財務概要"
        "payment_reminder" -> "支払いリマインダー"
        "no_valid_emi_due_date" -> "有効なEMI支払期日がありません"
        "emi_was_due_on" -> "EMIの支払期日は{date}でした"
        "emi_due_today" -> "EMIの支払期日は今日です"
        "reminder_due_in" -> "リマインダー：{lender}のEMIはあと{days}日です"
        "reminder_will_appear" -> "支払期日の{days}日前にリマインダーが表示されます"
        "due" -> "支払期日"
        "remind_me" -> "リマインドする"
        "test_notification" -> "通知をテスト"

        "everyday_expenses" -> "日常の支出"
        "track_daily_spending" -> "毎日の支出を記録・分析して、さらに節約しましょう。"
        "add_expense" -> "支出を追加"
        "expense_category" -> "支出カテゴリー"
        "select_category" -> "カテゴリーを選択"
        "amount" -> "金額"
        "enter_amount" -> "金額を入力"
        "note" -> "メモ"
        "enter_note" -> "メモを入力"
        "note_optional" -> "メモ（任意）"
        "date" -> "日付"

        "spending_limit" -> "支出上限"
        "spending_limits" -> "支出上限"
        "set_spending_limit" -> "支出上限を設定"
        "save_expense" -> "支出を保存"
        "update_expense" -> "支出を更新"
        "delete_expense" -> "支出を削除"
        "edit_expense" -> "支出を編集"

        "total_expenses" -> "総支出"
        "total_spent" -> "総支出額"
        "remaining_limit" -> "残りの上限"
        "expense_breakdown" -> "支出内訳"
        "expense_summary" -> "支出概要"
        "no_expenses" -> "まだ支出が追加されていません。"

        "expense_added" -> "支出が正常に追加されました。"
        "expense_updated" -> "支出が正常に更新されました。"
        "expense_deleted" -> "支出が正常に削除されました。"

        "invalid_amount" -> "無効な金額です。"
        "amount_required" -> "金額が必要です。"
        "category_required" -> "カテゴリーが必要です。"
        "category" -> "カテゴリー"
        "recent_expenses" -> "最近の支出"

        "current_limit" -> "現在の上限"
        "set_limit" -> "上限を設定"
        "limit_amount" -> "上限金額"

        "food" -> "食費"
        "travel" -> "旅行"
        "shopping" -> "ショッピング"
        "bills" -> "請求書"
        "entertainment" -> "娯楽"
        "other" -> "その他"

        "spending_limit_set" -> "支出上限が正常に設定されました。"
        "current_spending" -> "現在の支出"
        "limit" -> "上限"
        "remaining" -> "残り"

        "smart_alert" -> "スマートアラート"
        "smart_alerts" -> "スマートアラート"
        "spending_alerts" -> "支出アラート"
        "high_spending_alert" -> "高支出アラート"
        "category_limit_alert" -> "カテゴリー上限アラート"
        "budget_alert" -> "予算アラート"
        "high_spending" -> "高い支出"
        "approaching_limit" -> "上限に近づいています"

        "financial_health_tips" -> "財務健全性のヒント"
        "financial_tips" -> "財務のヒント"
        "tip" -> "ヒント"
        "tips" -> "ヒント"
        "save_money_tip" -> "毎日の支出を管理してお金を節約しましょう。"
        "track_expenses_tip" -> "定期的に支出を記録しましょう。"
        "avoid_unnecessary_spending_tip" -> "不要な支出を避けましょう。"
        "build_emergency_fund" -> "緊急資金を作る"
        "monitor_recurring" -> "定期的な支出を確認する"
        "avoid_new_emi" -> "過度な新しいEMIを避ける"
        "set_savings_goal" -> "貯蓄目標を設定する"

        "monthly_insights" -> "月間インサイト"
        "monthly_overview" -> "月間概要"
        "monthly_summary" -> "月間サマリー"
        "select_month" -> "月を選択"
        "this_month" -> "今月"
        "previous_month" -> "前月"
        "next_month" -> "翌月"
        "previous_months" -> "過去の月"
        "current_month" -> "現在の月"
        "total_monthly_spending" -> "月間総支出"
        "monthly_expenses" -> "月間支出"
        "total_spent_this_month" -> "今月の総支出"
        "category_breakdown" -> "カテゴリー別内訳"
        "highest_spending_category" -> "最も支出が多いカテゴリー"
        "lowest_spending_category" -> "最も支出が少ないカテゴリー"
        "average_daily_spending" -> "1日平均支出"
        "monthly_savings" -> "月間貯蓄"
        "spending_summary" -> "支出サマリー"
        "no_monthly_insights" -> "月間インサイトはありません。"
        "monthly_insight_message" -> "月間支出の概要です。"
        "no_data_for_month" -> "この月のデータはありません。"
        "monthly_total" -> "月間合計"
        "monthly_average" -> "月間平均"
        "daily_average" -> "日平均"
        "top_category" -> "主なカテゴリー"
        "highest_expense" -> "最大支出"
        "lowest_expense" -> "最小支出"
        "expense_trend" -> "支出傾向"
        "spending_pattern" -> "支出パターン"
        "compare_months" -> "月を比較"
        "monthly_report" -> "月間レポート"
        "summary" -> "概要"

        "smart_financial_decisions" -> "スマートな財務判断"
        "make_smarter_decisions" -> "財務データを使って、より賢い判断をしましょう。"
        "what_should_i_cut" -> "何を減らすべき？"
        "what_if" -> "もしこうしたら？"
        "see_reducing_spending" -> "支出を減らすと利用可能なお金にどのような影響があるか確認できます。"
        "current_available_money" -> "現在利用可能なお金"
        "available_money" -> "利用可能なお金"
        "available_after_suggested_cuts" -> "提案された削減後の利用可能額"
        "smart_suggestions" -> "スマートな提案"
        "top_suggestions" -> "主な提案"
        "suggestions" -> "提案"
        "recommendations" -> "おすすめ"
        "suggested_action" -> "おすすめの行動"
        "potential_savings" -> "節約可能額"
        "potential_monthly_savings" -> "月間節約可能額"
        "potential_monthly_savings_zero" -> "月間節約可能額：{amount}"

        "add_expenses_personalized" -> "パーソナライズされた提案を見るには、まず支出を追加してください。"
        "no_major_reduction" -> "現在の支出には大きな削減機会はありません。"
        "suggestions_current_spending" -> "提案は現在の支出パターンに基づいています。"

        "reduce_spending" -> "支出を減らす"
        "reduce_category" -> "カテゴリー支出を減らす"
        "save_more" -> "もっと節約する"
        "spending_habit" -> "支出習慣"
        "financial_decision" -> "財務判断"

        "cut_shopping" -> "ショッピング支出を減らす"
        "cut_food" -> "食費を減らす"
        "cut_entertainment" -> "娯楽費を減らす"
        "cut_travel" -> "旅行費を減らす"
        "cut_bills" -> "請求費を減らす"
        "cut_other" -> "その他の支出を減らす"

        "simple_actions_financial_situation" -> "あなたの財務状況に基づく簡単なアクションです。"
        "set_aside_available_money" -> "毎月、利用可能なお金の一部を貯めてみましょう。"
        "review_subscriptions" -> "サブスクリプションや定期的な支出を定期的に確認しましょう。"
        "check_existing_emi" -> "新しいEMIを追加する前に、既存のEMI負担を確認してください。"
        "use_savings_goal" -> "月間節約可能額を貯蓄目標のスタート地点として使いましょう。"
        "set_monthly_limits" -> "各カテゴリーに月間上限を設定しましょう。"
        "spending_alerts_actual" -> "実際の支出に基づいた支出アラートです。"
        "latest_entries" -> "最新の記録です。"

        "cut" -> "削減"
        "suggested" -> "おすすめ"
        "spent" -> "支出済み"
        "keep_it_up" -> "この調子で続けましょう！"
        "view_summary" -> "概要を見る"
        "apply_suggestion" -> "提案を適用"

        "date_not_set" -> "日付が設定されていません"
        "enter_valid_amount" -> "有効な金額を入力してください。"
        "enter_valid_limit" -> "有効な上限を入力してください。"

        "where_did_you_spend" -> "どこで使いましたか？"
        "expense_note" -> "支出メモ"
        "today" -> "今日"
        "yesterday" -> "昨日"

        "back" -> "戻る"

        else -> englishText(key)
    }
}


// ============================================================
// GERMAN
// ============================================================

fun germanText(key: String): String {

    return when (key) {

        "paywise" -> "PayWise"
        "tagline" -> "Clever ausgeben, clever sparen."

        "login" -> "Anmelden"
        "signup" -> "Neues Konto erstellen"
        "create_account" -> "Konto erstellen"
        "back_to_login" -> "Zur Anmeldung"
        "create_your_account" -> "Erstellen Sie Ihr Konto"

        "full_name" -> "Vollständiger Name"
        "email" -> "E-Mail-Adresse"
        "password" -> "Passwort"
        "confirm_password" -> "Passwort bestätigen"
        "mobile" -> "Mobilnummer"

        "dashboard" -> "Dashboard"
        "add_salary" -> "Gehalt hinzufügen"
        "my_emis" -> "Meine EMIs"
        "add_emi" -> "EMI hinzufügen"
        "financial_summary" -> "Finanzübersicht"
        "profile" -> "Profil"
        "settings" -> "Einstellungen"
        "logout" -> "Abmelden"

        "welcome" -> "Willkommen"
        "manage_finances" -> "Verwalten Sie Ihre Finanzen sinnvoll."
        "loading_financial_data" -> "Finanzdaten werden geladen..."

        "monthly_salary" -> "Monatliches Gehalt"
        "total_emi" -> "Gesamte EMI"
        "remaining_salary" -> "Verbleibendes Gehalt"
        "emi_ratio" -> "EMI-Verhältnis"
        "financial_status" -> "Finanzstatus"
        "your_emis" -> "Ihre EMIs"

        "no_emi_records" -> "Keine EMI-Einträge gefunden."
        "add_update_salary" -> "Gehalt hinzufügen / aktualisieren"
        "save_salary" -> "Gehalt speichern"
        "enter_valid_salary" -> "Geben Sie ein gültiges Gehalt ein."
        "salary_updated" -> "Gehalt erfolgreich aktualisiert."

        "bank_lender" -> "Bank / Kreditgeber"
        "emi_amount" -> "EMI-Betrag"
        "due_date" -> "Fälligkeitsdatum"
        "frequency" -> "Häufigkeit"
        "monthly" -> "Monatlich"

        "valid_emi" -> "Bitte geben Sie gültige EMI-Daten ein."
        "emi_added" -> "EMI erfolgreich hinzugefügt."
        "no_financial_data" -> "Keine Finanzdaten verfügbar."

        "my_profile" -> "Mein Profil"
        "profile_information" -> "Profilinformationen"
        "personal_information" -> "Persönliche Informationen"
        "not_available" -> "Nicht verfügbar"

        "notifications" -> "Benachrichtigungen"
        "emi_due_reminders" -> "EMI-Fälligkeitserinnerungen"
        "payment_reminders" -> "Zahlungserinnerungen"
        "reminder_days" -> "Erinnerungstage"
        "days" -> "Tage"

        "financial_preferences" -> "Finanzeinstellungen"
        "currency" -> "Währung"
        "primary_currency" -> "Hauptwährung"
        "show_financial_status" -> "Finanzstatus anzeigen"

        "language" -> "Sprache"
        "app_language" -> "App-Sprache"
        "region" -> "Region"
        "country_region" -> "Land / Region"

        "about_paywise" -> "Über PayWise"
        "version" -> "Version 1.0"

        "select_currency" -> "Währung auswählen"
        "search_currency" -> "Währung oder Code suchen"
        "select_country" -> "Land / Region auswählen"
        "search_country" -> "Land suchen"
        "select_language" -> "Sprache auswählen"
        "search_language" -> "Sprache suchen"

        "no_results" -> "Keine Ergebnisse gefunden"
        "close" -> "Schließen"

        "please_fill_all" -> "Bitte füllen Sie alle Felder aus."
        "passwords_not_match" -> "Die Passwörter stimmen nicht überein."
        "please_enter_login" -> "Bitte E-Mail und Passwort eingeben."

        "profile_photo" -> "Profilfoto"
        "select_photo" -> "Foto auswählen"
        "edit_profile" -> "Profil bearbeiten"
        "financial_profile" -> "Finanzprofil"
        "total_active_emis" -> "Aktive EMIs insgesamt"
        "total_monthly_emi" -> "Monatliche EMI-Verpflichtung"

        "account_information" -> "Kontoinformationen"
        "account_creation_date" -> "Kontoerstellungsdatum"
        "user_id" -> "Benutzer-ID"

        "security" -> "Sicherheit"
        "change_password" -> "Passwort ändern"
        "current_password" -> "Aktuelles Passwort"
        "new_password" -> "Neues Passwort"
        "save_changes" -> "Änderungen speichern"
        "cancel" -> "Abbrechen"

        "financial_overview" -> "Finanzübersicht"
        "payment_reminder" -> "Zahlungserinnerung"
        "no_valid_emi_due_date" -> "Kein gültiges EMI-Fälligkeitsdatum verfügbar"
        "emi_was_due_on" -> "EMI war am {date} fällig"
        "emi_due_today" -> "EMI ist heute fällig"
        "reminder_due_in" -> "Erinnerung: EMI von {lender} ist in {days} Tagen fällig"
        "reminder_will_appear" -> "Die Erinnerung erscheint {days} Tage vor dem Fälligkeitsdatum"
        "due" -> "Fällig"
        "remind_me" -> "Mich erinnern"
        "test_notification" -> "Testbenachrichtigung"

        "everyday_expenses" -> "Alltägliche Ausgaben"
        "track_daily_spending" -> "Verfolgen und analysieren Sie Ihre täglichen Ausgaben und sparen Sie mehr."
        "add_expense" -> "Ausgabe hinzufügen"
        "expense_category" -> "Ausgabenkategorie"
        "select_category" -> "Kategorie auswählen"
        "amount" -> "Betrag"
        "enter_amount" -> "Betrag eingeben"
        "note" -> "Notiz"
        "enter_note" -> "Notiz eingeben"
        "note_optional" -> "Notiz (optional)"
        "date" -> "Datum"

        "spending_limit" -> "Ausgabenlimit"
        "spending_limits" -> "Ausgabenlimits"
        "set_spending_limit" -> "Ausgabenlimit festlegen"
        "save_expense" -> "Ausgabe speichern"
        "update_expense" -> "Ausgabe aktualisieren"
        "delete_expense" -> "Ausgabe löschen"
        "edit_expense" -> "Ausgabe bearbeiten"

        "total_expenses" -> "Gesamtausgaben"
        "total_spent" -> "Insgesamt ausgegeben"
        "remaining_limit" -> "Verbleibendes Limit"
        "expense_breakdown" -> "Ausgabenübersicht"
        "expense_summary" -> "Ausgabenzusammenfassung"
        "no_expenses" -> "Noch keine Ausgaben hinzugefügt."

        "expense_added" -> "Ausgabe erfolgreich hinzugefügt."
        "expense_updated" -> "Ausgabe erfolgreich aktualisiert."
        "expense_deleted" -> "Ausgabe erfolgreich gelöscht."

        "invalid_amount" -> "Ungültiger Betrag."
        "amount_required" -> "Betrag erforderlich."
        "category_required" -> "Kategorie erforderlich."
        "category" -> "Kategorie"
        "recent_expenses" -> "Letzte Ausgaben"

        "current_limit" -> "Aktuelles Limit"
        "set_limit" -> "Limit festlegen"
        "limit_amount" -> "Limitbetrag"

        "food" -> "Essen"
        "travel" -> "Reisen"
        "shopping" -> "Einkaufen"
        "bills" -> "Rechnungen"
        "entertainment" -> "Unterhaltung"
        "other" -> "Sonstiges"

        "spending_limit_set" -> "Ausgabenlimit erfolgreich festgelegt."
        "current_spending" -> "Aktuelle Ausgaben"
        "limit" -> "Limit"
        "remaining" -> "Verbleibend"

        "smart_alert" -> "Intelligenter Alarm"
        "smart_alerts" -> "Intelligente Alarme"
        "spending_alerts" -> "Ausgabenalarme"
        "high_spending_alert" -> "Alarm für hohe Ausgaben"
        "category_limit_alert" -> "Kategorielimit-Alarm"
        "budget_alert" -> "Budgetalarm"
        "high_spending" -> "Hohe Ausgaben"
        "approaching_limit" -> "Limit wird erreicht"

        "financial_health_tips" -> "Tipps für finanzielle Gesundheit"
        "financial_tips" -> "Finanztipps"
        "tip" -> "Tipp"
        "tips" -> "Tipps"
        "save_money_tip" -> "Sparen Sie Geld, indem Sie Ihre täglichen Ausgaben überwachen."
        "track_expenses_tip" -> "Verfolgen Sie Ihre Ausgaben regelmäßig."
        "avoid_unnecessary_spending_tip" -> "Vermeiden Sie unnötige Ausgaben."
        "build_emergency_fund" -> "Notfallfonds aufbauen"
        "monitor_recurring" -> "Wiederkehrende Ausgaben überwachen"
        "avoid_new_emi" -> "Übermäßige neue EMIs vermeiden"
        "set_savings_goal" -> "Sparziel festlegen"

        "monthly_insights" -> "Monatliche Einblicke"
        "monthly_overview" -> "Monatlicher Überblick"
        "monthly_summary" -> "Monatliche Zusammenfassung"
        "select_month" -> "Monat auswählen"
        "this_month" -> "Dieser Monat"
        "previous_month" -> "Letzter Monat"
        "next_month" -> "Nächster Monat"
        "previous_months" -> "Vorherige Monate"
        "current_month" -> "Aktueller Monat"
        "total_monthly_spending" -> "Monatliche Gesamtausgaben"
        "monthly_expenses" -> "Monatliche Ausgaben"
        "total_spent_this_month" -> "Diesen Monat insgesamt ausgegeben"
        "category_breakdown" -> "Aufschlüsselung nach Kategorie"
        "highest_spending_category" -> "Kategorie mit höchsten Ausgaben"
        "lowest_spending_category" -> "Kategorie mit niedrigsten Ausgaben"
        "average_daily_spending" -> "Durchschnittliche tägliche Ausgaben"
        "monthly_savings" -> "Monatliche Ersparnis"
        "spending_summary" -> "Ausgabenübersicht"
        "no_monthly_insights" -> "Keine monatlichen Einblicke verfügbar."
        "monthly_insight_message" -> "Hier ist ein Überblick über Ihre monatlichen Ausgaben."
        "no_data_for_month" -> "Für diesen Monat sind keine Daten verfügbar."
        "monthly_total" -> "Monatliche Summe"
        "monthly_average" -> "Monatlicher Durchschnitt"
        "daily_average" -> "Tagesdurchschnitt"
        "top_category" -> "Top-Kategorie"
        "highest_expense" -> "Höchste Ausgabe"
        "lowest_expense" -> "Niedrigste Ausgabe"
        "expense_trend" -> "Ausgabentrend"
        "spending_pattern" -> "Ausgabenmuster"
        "compare_months" -> "Monate vergleichen"
        "monthly_report" -> "Monatsbericht"
        "summary" -> "Zusammenfassung"

        "smart_financial_decisions" -> "Intelligente Finanzentscheidungen"
        "make_smarter_decisions" -> "Treffen Sie intelligentere Entscheidungen mit Ihren Finanzdaten."
        "what_should_i_cut" -> "Was sollte ich reduzieren?"
        "what_if" -> "Was wäre, wenn?"
        "see_reducing_spending" -> "Sehen Sie, wie geringere Ausgaben Ihr verfügbares Geld beeinflussen können."
        "current_available_money" -> "Aktuell verfügbares Geld"
        "available_money" -> "Verfügbares Geld"
        "available_after_suggested_cuts" -> "Verfügbares Geld nach vorgeschlagenen Kürzungen"
        "smart_suggestions" -> "Intelligente Vorschläge"
        "top_suggestions" -> "Top-Vorschläge"
        "suggestions" -> "Vorschläge"
        "recommendations" -> "Empfehlungen"
        "suggested_action" -> "Vorgeschlagene Aktion"
        "potential_savings" -> "Mögliche Ersparnis"
        "potential_monthly_savings" -> "Mögliche monatliche Ersparnis"
        "potential_monthly_savings_zero" -> "Mögliche monatliche Ersparnis: {amount}"

        "add_expenses_personalized" -> "Fügen Sie zuerst einige Ausgaben hinzu, um personalisierte Vorschläge zu erhalten."
        "no_major_reduction" -> "Ihre aktuellen Ausgaben zeigen keine großen Einsparmöglichkeiten."
        "suggestions_current_spending" -> "Die Vorschläge basieren auf Ihrem aktuellen Ausgabenverhalten."

        "reduce_spending" -> "Ausgaben reduzieren"
        "reduce_category" -> "Kategorieausgaben reduzieren"
        "save_more" -> "Mehr sparen"
        "spending_habit" -> "Ausgabenverhalten"
        "financial_decision" -> "Finanzentscheidung"

        "cut_shopping" -> "Shopping-Ausgaben reduzieren"
        "cut_food" -> "Essensausgaben reduzieren"
        "cut_entertainment" -> "Unterhaltungsausgaben reduzieren"
        "cut_travel" -> "Reiseausgaben reduzieren"
        "cut_bills" -> "Rechnungsausgaben reduzieren"
        "cut_other" -> "Sonstige Ausgaben reduzieren"

        "simple_actions_financial_situation" -> "Einfache Maßnahmen basierend auf Ihrer finanziellen Situation."
        "set_aside_available_money" -> "Legen Sie jeden Monat einen Teil Ihres verfügbaren Geldes zurück."
        "review_subscriptions" -> "Überprüfen Sie regelmäßig Abonnements und wiederkehrende Ausgaben."
        "check_existing_emi" -> "Prüfen Sie Ihre bestehenden EMI-Verpflichtungen, bevor Sie eine weitere EMI hinzufügen."
        "use_savings_goal" -> "Verwenden Sie Ihre mögliche monatliche Ersparnis als Startziel."
        "set_monthly_limits" -> "Setzen Sie monatliche Limits für Ihre Kategorien."
        "spending_alerts_actual" -> "Ausgabenalarme basierend auf Ihren tatsächlichen Ausgaben."
        "latest_entries" -> "Ihre neuesten Einträge."

        "cut" -> "Reduzieren"
        "suggested" -> "Vorgeschlagen"
        "spent" -> "Ausgegeben"
        "keep_it_up" -> "Weiter so!"
        "view_summary" -> "Zusammenfassung anzeigen"
        "apply_suggestion" -> "Vorschlag anwenden"

        "date_not_set" -> "Datum nicht festgelegt"
        "enter_valid_amount" -> "Geben Sie einen gültigen Betrag ein."
        "enter_valid_limit" -> "Geben Sie ein gültiges Limit ein."

        "where_did_you_spend" -> "Wo haben Sie ausgegeben?"
        "expense_note" -> "Ausgabennotiz"
        "today" -> "Heute"
        "yesterday" -> "Gestern"

        "back" -> "Zurück"

        else -> englishText(key)
    }
}


// ============================================================
// SPANISH
// ============================================================

fun spanishText(key: String): String {

    return when (key) {

        "paywise" -> "PayWise"
        "tagline" -> "Gasta de forma inteligente, ahorra de forma inteligente."

        "login" -> "Iniciar sesión"
        "signup" -> "Crear nueva cuenta"
        "create_account" -> "Crear cuenta"
        "back_to_login" -> "Volver al inicio de sesión"
        "create_your_account" -> "Crea tu cuenta"

        "full_name" -> "Nombre completo"
        "email" -> "Correo electrónico"
        "password" -> "Contraseña"
        "confirm_password" -> "Confirmar contraseña"
        "mobile" -> "Número de móvil"

        "dashboard" -> "Panel"
        "add_salary" -> "Añadir salario"
        "my_emis" -> "Mis EMI"
        "add_emi" -> "Añadir EMI"
        "financial_summary" -> "Resumen financiero"
        "profile" -> "Perfil"
        "settings" -> "Configuración"
        "logout" -> "Cerrar sesión"

        "welcome" -> "Bienvenido"
        "manage_finances" -> "Administra tus finanzas de forma inteligente."
        "loading_financial_data" -> "Cargando datos financieros..."

        "monthly_salary" -> "Salario mensual"
        "total_emi" -> "EMI total"
        "remaining_salary" -> "Salario restante"
        "emi_ratio" -> "Ratio de EMI"
        "financial_status" -> "Estado financiero"
        "your_emis" -> "Tus EMI"

        "no_emi_records" -> "No se encontraron registros de EMI."
        "add_update_salary" -> "Añadir / actualizar salario"
        "save_salary" -> "Guardar salario"
        "enter_valid_salary" -> "Introduce un salario válido."
        "salary_updated" -> "Salario actualizado correctamente."

        "bank_lender" -> "Banco / Prestamista"
        "emi_amount" -> "Importe de EMI"
        "due_date" -> "Fecha de vencimiento"
        "frequency" -> "Frecuencia"
        "monthly" -> "Mensual"

        "valid_emi" -> "Introduce datos de EMI válidos."
        "emi_added" -> "EMI añadida correctamente."
        "no_financial_data" -> "No hay datos financieros disponibles."

        "my_profile" -> "Mi perfil"
        "profile_information" -> "Información del perfil"
        "personal_information" -> "Información personal"
        "not_available" -> "No disponible"

        "notifications" -> "Notificaciones"
        "emi_due_reminders" -> "Recordatorios de vencimiento de EMI"
        "payment_reminders" -> "Recordatorios de pago"
        "reminder_days" -> "Días de recordatorio"
        "days" -> "días"

        "financial_preferences" -> "Preferencias financieras"
        "currency" -> "Moneda"
        "primary_currency" -> "Moneda principal"
        "show_financial_status" -> "Mostrar estado financiero"

        "language" -> "Idioma"
        "app_language" -> "Idioma de la aplicación"
        "region" -> "Región"
        "country_region" -> "País / Región"

        "about_paywise" -> "Acerca de PayWise"
        "version" -> "Versión 1.0"

        "select_currency" -> "Seleccionar moneda"
        "search_currency" -> "Buscar moneda o código"
        "select_country" -> "Seleccionar país / región"
        "search_country" -> "Buscar país"
        "select_language" -> "Seleccionar idioma"
        "search_language" -> "Buscar idioma"

        "no_results" -> "No se encontraron resultados"
        "close" -> "Cerrar"

        "please_fill_all" -> "Completa todos los campos."
        "passwords_not_match" -> "Las contraseñas no coinciden."
        "please_enter_login" -> "Introduce el correo electrónico y la contraseña."

        "profile_photo" -> "Foto de perfil"
        "select_photo" -> "Seleccionar foto"
        "edit_profile" -> "Editar perfil"
        "financial_profile" -> "Perfil financiero"
        "total_active_emis" -> "EMI activas totales"
        "total_monthly_emi" -> "Compromiso total de EMI mensual"

        "account_information" -> "Información de la cuenta"
        "account_creation_date" -> "Fecha de creación de la cuenta"
        "user_id" -> "ID de usuario"

        "security" -> "Seguridad"
        "change_password" -> "Cambiar contraseña"
        "current_password" -> "Contraseña actual"
        "new_password" -> "Nueva contraseña"
        "save_changes" -> "Guardar cambios"
        "cancel" -> "Cancelar"

        "financial_overview" -> "Resumen financiero"
        "payment_reminder" -> "Recordatorio de pago"
        "no_valid_emi_due_date" -> "No hay una fecha de vencimiento de EMI válida"
        "emi_was_due_on" -> "La EMI vencía el {date}"
        "emi_due_today" -> "La EMI vence hoy"
        "reminder_due_in" -> "Recordatorio: la EMI de {lender} vence en {days} días"
        "reminder_will_appear" -> "El recordatorio aparecerá {days} días antes de la fecha de vencimiento"
        "due" -> "Vencimiento"
        "remind_me" -> "Recordarme"
        "test_notification" -> "Notificación de prueba"

        "everyday_expenses" -> "Gastos diarios"
        "track_daily_spending" -> "Controla y analiza tus gastos diarios para ahorrar más."
        "add_expense" -> "Añadir gasto"
        "expense_category" -> "Categoría de gasto"
        "select_category" -> "Seleccionar categoría"
        "amount" -> "Cantidad"
        "enter_amount" -> "Introducir cantidad"
        "note" -> "Nota"
        "enter_note" -> "Introducir nota"
        "note_optional" -> "Nota (opcional)"
        "date" -> "Fecha"

        "spending_limit" -> "Límite de gasto"
        "spending_limits" -> "Límites de gasto"
        "set_spending_limit" -> "Establecer límite de gasto"
        "save_expense" -> "Guardar gasto"
        "update_expense" -> "Actualizar gasto"
        "delete_expense" -> "Eliminar gasto"
        "edit_expense" -> "Editar gasto"

        "total_expenses" -> "Gastos totales"
        "total_spent" -> "Total gastado"
        "remaining_limit" -> "Límite restante"
        "expense_breakdown" -> "Desglose de gastos"
        "expense_summary" -> "Resumen de gastos"
        "no_expenses" -> "Aún no se han añadido gastos."

        "expense_added" -> "Gasto añadido correctamente."
        "expense_updated" -> "Gasto actualizado correctamente."
        "expense_deleted" -> "Gasto eliminado correctamente."

        "invalid_amount" -> "Cantidad no válida."
        "amount_required" -> "La cantidad es obligatoria."
        "category_required" -> "La categoría es obligatoria."
        "category" -> "Categoría"
        "recent_expenses" -> "Gastos recientes"

        "current_limit" -> "Límite actual"
        "set_limit" -> "Establecer límite"
        "limit_amount" -> "Cantidad límite"

        "food" -> "Comida"
        "travel" -> "Viajes"
        "shopping" -> "Compras"
        "bills" -> "Facturas"
        "entertainment" -> "Entretenimiento"
        "other" -> "Otros"

        "spending_limit_set" -> "Límite de gasto establecido correctamente."
        "current_spending" -> "Gasto actual"
        "limit" -> "Límite"
        "remaining" -> "Restante"

        "smart_alert" -> "Alerta inteligente"
        "smart_alerts" -> "Alertas inteligentes"
        "spending_alerts" -> "Alertas de gasto"
        "high_spending_alert" -> "Alerta de gasto elevado"
        "category_limit_alert" -> "Alerta de límite de categoría"
        "budget_alert" -> "Alerta de presupuesto"
        "high_spending" -> "Gasto elevado"
        "approaching_limit" -> "Acercándose al límite"

        "financial_health_tips" -> "Consejos de salud financiera"
        "financial_tips" -> "Consejos financieros"
        "tip" -> "Consejo"
        "tips" -> "Consejos"
        "save_money_tip" -> "Ahorra dinero controlando tus gastos diarios."
        "track_expenses_tip" -> "Controla tus gastos regularmente."
        "avoid_unnecessary_spending_tip" -> "Evita gastos innecesarios."
        "build_emergency_fund" -> "Crea un fondo de emergencia"
        "monitor_recurring" -> "Controla los gastos recurrentes"
        "avoid_new_emi" -> "Evita demasiadas EMI nuevas"
        "set_savings_goal" -> "Establece un objetivo de ahorro"

        "monthly_insights" -> "Información mensual"
        "monthly_overview" -> "Resumen mensual"
        "monthly_summary" -> "Resumen del mes"
        "select_month" -> "Seleccionar mes"
        "this_month" -> "Este mes"
        "previous_month" -> "Mes anterior"
        "next_month" -> "Mes siguiente"
        "previous_months" -> "Meses anteriores"
        "current_month" -> "Mes actual"
        "total_monthly_spending" -> "Gasto mensual total"
        "monthly_expenses" -> "Gastos mensuales"
        "total_spent_this_month" -> "Total gastado este mes"
        "category_breakdown" -> "Desglose por categoría"
        "highest_spending_category" -> "Categoría con mayor gasto"
        "lowest_spending_category" -> "Categoría con menor gasto"
        "average_daily_spending" -> "Gasto diario promedio"
        "monthly_savings" -> "Ahorro mensual"
        "spending_summary" -> "Resumen de gastos"
        "no_monthly_insights" -> "No hay información mensual disponible."
        "monthly_insight_message" -> "Aquí tienes un resumen de tus gastos mensuales."
        "no_data_for_month" -> "No hay datos para este mes."
        "monthly_total" -> "Total mensual"
        "monthly_average" -> "Promedio mensual"
        "daily_average" -> "Promedio diario"
        "top_category" -> "Categoría principal"
        "highest_expense" -> "Mayor gasto"
        "lowest_expense" -> "Menor gasto"
        "expense_trend" -> "Tendencia de gastos"
        "spending_pattern" -> "Patrón de gasto"
        "compare_months" -> "Comparar meses"
        "monthly_report" -> "Informe mensual"
        "summary" -> "Resumen"

        "smart_financial_decisions" -> "Decisiones financieras inteligentes"
        "make_smarter_decisions" -> "Toma decisiones más inteligentes usando tus datos financieros."
        "what_should_i_cut" -> "¿Qué debería reducir?"
        "what_if" -> "¿Y si?"
        "see_reducing_spending" -> "Mira cómo reducir tus gastos puede afectar a tu dinero disponible."
        "current_available_money" -> "Dinero disponible actual"
        "available_money" -> "Dinero disponible"
        "available_after_suggested_cuts" -> "Dinero disponible después de las reducciones sugeridas"
        "smart_suggestions" -> "Sugerencias inteligentes"
        "top_suggestions" -> "Principales sugerencias"
        "suggestions" -> "Sugerencias"
        "recommendations" -> "Recomendaciones"
        "suggested_action" -> "Acción sugerida"
        "potential_savings" -> "Ahorro potencial"
        "potential_monthly_savings" -> "Ahorro mensual potencial"
        "potential_monthly_savings_zero" -> "Ahorro mensual potencial: {amount}"

        "add_expenses_personalized" -> "Añade algunos gastos primero para obtener sugerencias personalizadas."
        "no_major_reduction" -> "Tus gastos actuales no muestran una gran oportunidad de reducción."
        "suggestions_current_spending" -> "Las sugerencias se basan en tus patrones de gasto actuales."

        "reduce_spending" -> "Reducir gastos"
        "reduce_category" -> "Reducir gastos de categoría"
        "save_more" -> "Ahorrar más"
        "spending_habit" -> "Hábito de gasto"
        "financial_decision" -> "Decisión financiera"

        "cut_shopping" -> "Reducir gastos de compras"
        "cut_food" -> "Reducir gastos de comida"
        "cut_entertainment" -> "Reducir gastos de entretenimiento"
        "cut_travel" -> "Reducir gastos de viaje"
        "cut_bills" -> "Reducir gastos de facturas"
        "cut_other" -> "Reducir otros gastos"

        "simple_actions_financial_situation" -> "Acciones sencillas basadas en tu situación financiera."
        "set_aside_available_money" -> "Intenta reservar una parte de tu dinero disponible cada mes."
        "review_subscriptions" -> "Revisa periódicamente las suscripciones y los gastos recurrentes."
        "check_existing_emi" -> "Comprueba tus compromisos actuales de EMI antes de añadir otra EMI."
        "use_savings_goal" -> "Usa tus posibles ahorros mensuales como objetivo inicial."
        "set_monthly_limits" -> "Establece límites mensuales para tus categorías."
        "spending_alerts_actual" -> "Alertas de gasto basadas en tus gastos reales."
        "latest_entries" -> "Tus últimas entradas."

        "cut" -> "Reducir"
        "suggested" -> "Sugerido"
        "spent" -> "Gastado"
        "keep_it_up" -> "¡Sigue así!"
        "view_summary" -> "Ver resumen"
        "apply_suggestion" -> "Aplicar sugerencia"

        "date_not_set" -> "Fecha no establecida"
        "enter_valid_amount" -> "Introduce una cantidad válida."
        "enter_valid_limit" -> "Introduce un límite válido."

        "where_did_you_spend" -> "¿Dónde gastaste?"
        "expense_note" -> "Nota del gasto"
        "today" -> "Hoy"
        "yesterday" -> "Ayer"

        "back" -> "Atrás"

        else -> englishText(key)
    }
}


// ============================================================
// FRENCH
// ============================================================

fun frenchText(key: String): String {

    return when (key) {

        "paywise" -> "PayWise"
        "tagline" -> "Dépensez intelligemment, épargnez intelligemment."

        "login" -> "Connexion"
        "signup" -> "Créer un nouveau compte"
        "create_account" -> "Créer un compte"
        "back_to_login" -> "Retour à la connexion"
        "create_your_account" -> "Créez votre compte"

        "full_name" -> "Nom complet"
        "email" -> "Adresse e-mail"
        "password" -> "Mot de passe"
        "confirm_password" -> "Confirmer le mot de passe"
        "mobile" -> "Numéro de téléphone"

        "dashboard" -> "Tableau de bord"
        "add_salary" -> "Ajouter le salaire"
        "my_emis" -> "Mes EMI"
        "add_emi" -> "Ajouter une EMI"
        "financial_summary" -> "Résumé financier"
        "profile" -> "Profil"
        "settings" -> "Paramètres"
        "logout" -> "Déconnexion"

        "welcome" -> "Bienvenue"
        "manage_finances" -> "Gérez vos finances intelligemment."
        "loading_financial_data" -> "Chargement des données financières..."

        "monthly_salary" -> "Salaire mensuel"
        "total_emi" -> "EMI totale"
        "remaining_salary" -> "Salaire restant"
        "emi_ratio" -> "Ratio EMI"
        "financial_status" -> "Situation financière"
        "your_emis" -> "Vos EMI"

        "no_emi_records" -> "Aucun enregistrement EMI trouvé."
        "add_update_salary" -> "Ajouter / mettre à jour le salaire"
        "save_salary" -> "Enregistrer le salaire"
        "enter_valid_salary" -> "Entrez un salaire valide."
        "salary_updated" -> "Salaire mis à jour avec succès."

        "bank_lender" -> "Banque / prêteur"
        "emi_amount" -> "Montant EMI"
        "due_date" -> "Date d'échéance"
        "frequency" -> "Fréquence"
        "monthly" -> "Mensuel"

        "valid_emi" -> "Veuillez entrer des informations EMI valides."
        "emi_added" -> "EMI ajoutée avec succès."
        "no_financial_data" -> "Aucune donnée financière disponible."

        "my_profile" -> "Mon profil"
        "profile_information" -> "Informations du profil"
        "personal_information" -> "Informations personnelles"
        "not_available" -> "Non disponible"

        "notifications" -> "Notifications"
        "emi_due_reminders" -> "Rappels d'échéance EMI"
        "payment_reminders" -> "Rappels de paiement"
        "reminder_days" -> "Jours de rappel"
        "days" -> "jours"

        "financial_preferences" -> "Préférences financières"
        "currency" -> "Devise"
        "primary_currency" -> "Devise principale"
        "show_financial_status" -> "Afficher la situation financière"

        "language" -> "Langue"
        "app_language" -> "Langue de l'application"
        "region" -> "Région"
        "country_region" -> "Pays / Région"

        "about_paywise" -> "À propos de PayWise"
        "version" -> "Version 1.0"

        "select_currency" -> "Sélectionner la devise"
        "search_currency" -> "Rechercher une devise ou un code"
        "select_country" -> "Sélectionner le pays / la région"
        "search_country" -> "Rechercher un pays"
        "select_language" -> "Sélectionner la langue"
        "search_language" -> "Rechercher une langue"

        "no_results" -> "Aucun résultat trouvé"
        "close" -> "Fermer"

        "please_fill_all" -> "Veuillez remplir tous les champs."
        "passwords_not_match" -> "Les mots de passe ne correspondent pas."
        "please_enter_login" -> "Veuillez entrer votre e-mail et votre mot de passe."

        "profile_photo" -> "Photo de profil"
        "select_photo" -> "Sélectionner une photo"
        "edit_profile" -> "Modifier le profil"
        "financial_profile" -> "Profil financier"
        "total_active_emis" -> "Total des EMI actives"
        "total_monthly_emi" -> "Engagement EMI mensuel total"

        "account_information" -> "Informations du compte"
        "account_creation_date" -> "Date de création du compte"
        "user_id" -> "ID utilisateur"

        "security" -> "Sécurité"
        "change_password" -> "Modifier le mot de passe"
        "current_password" -> "Mot de passe actuel"
        "new_password" -> "Nouveau mot de passe"
        "save_changes" -> "Enregistrer les modifications"
        "cancel" -> "Annuler"

        "financial_overview" -> "Aperçu financier"
        "payment_reminder" -> "Rappel de paiement"
        "no_valid_emi_due_date" -> "Aucune date d'échéance EMI valide disponible"
        "emi_was_due_on" -> "L'EMI était due le {date}"
        "emi_due_today" -> "L'EMI est due aujourd'hui"
        "reminder_due_in" -> "Rappel : l'EMI de {lender} est due dans {days} jours"
        "reminder_will_appear" -> "Le rappel apparaîtra {days} jours avant la date d'échéance"
        "due" -> "Échéance"
        "remind_me" -> "Me rappeler"
        "test_notification" -> "Notification de test"

        "everyday_expenses" -> "Dépenses quotidiennes"
        "track_daily_spending" -> "Suivez et analysez vos dépenses quotidiennes pour économiser davantage."
        "add_expense" -> "Ajouter une dépense"
        "expense_category" -> "Catégorie de dépense"
        "select_category" -> "Sélectionner une catégorie"
        "amount" -> "Montant"
        "enter_amount" -> "Entrer le montant"
        "note" -> "Note"
        "enter_note" -> "Entrer une note"
        "note_optional" -> "Note (facultatif)"
        "date" -> "Date"

        "spending_limit" -> "Limite de dépenses"
        "spending_limits" -> "Limites de dépenses"
        "set_spending_limit" -> "Définir une limite de dépenses"
        "save_expense" -> "Enregistrer la dépense"
        "update_expense" -> "Mettre à jour la dépense"
        "delete_expense" -> "Supprimer la dépense"
        "edit_expense" -> "Modifier la dépense"

        "total_expenses" -> "Dépenses totales"
        "total_spent" -> "Total dépensé"
        "remaining_limit" -> "Limite restante"
        "expense_breakdown" -> "Répartition des dépenses"
        "expense_summary" -> "Résumé des dépenses"
        "no_expenses" -> "Aucune dépense ajoutée pour le moment."

        "expense_added" -> "Dépense ajoutée avec succès."
        "expense_updated" -> "Dépense mise à jour avec succès."
        "expense_deleted" -> "Dépense supprimée avec succès."

        "invalid_amount" -> "Montant invalide."
        "amount_required" -> "Le montant est requis."
        "category_required" -> "La catégorie est requise."
        "category" -> "Catégorie"
        "recent_expenses" -> "Dépenses récentes"

        "current_limit" -> "Limite actuelle"
        "set_limit" -> "Définir la limite"
        "limit_amount" -> "Montant de la limite"

        "food" -> "Alimentation"
        "travel" -> "Voyage"
        "shopping" -> "Achats"
        "bills" -> "Factures"
        "entertainment" -> "Divertissement"
        "other" -> "Autre"

        "spending_limit_set" -> "Limite de dépenses définie avec succès."
        "current_spending" -> "Dépenses actuelles"
        "limit" -> "Limite"
        "remaining" -> "Restant"

        "smart_alert" -> "Alerte intelligente"
        "smart_alerts" -> "Alertes intelligentes"
        "spending_alerts" -> "Alertes de dépenses"
        "high_spending_alert" -> "Alerte de dépenses élevées"
        "category_limit_alert" -> "Alerte de limite de catégorie"
        "budget_alert" -> "Alerte budgétaire"
        "high_spending" -> "Dépenses élevées"
        "approaching_limit" -> "Proche de la limite"

        "financial_health_tips" -> "Conseils de santé financière"
        "financial_tips" -> "Conseils financiers"
        "tip" -> "Conseil"
        "tips" -> "Conseils"
        "save_money_tip" -> "Économisez en surveillant vos dépenses quotidiennes."
        "track_expenses_tip" -> "Suivez régulièrement vos dépenses."
        "avoid_unnecessary_spending_tip" -> "Évitez les dépenses inutiles."
        "build_emergency_fund" -> "Créer un fonds d'urgence"
        "monitor_recurring" -> "Surveiller les dépenses récurrentes"
        "avoid_new_emi" -> "Éviter les nouvelles EMI excessives"
        "set_savings_goal" -> "Définir un objectif d'épargne"

        "monthly_insights" -> "Aperçus mensuels"
        "monthly_overview" -> "Aperçu mensuel"
        "monthly_summary" -> "Résumé mensuel"
        "select_month" -> "Sélectionner le mois"
        "this_month" -> "Ce mois-ci"
        "previous_month" -> "Mois précédent"
        "next_month" -> "Mois suivant"
        "previous_months" -> "Mois précédents"
        "current_month" -> "Mois actuel"
        "total_monthly_spending" -> "Dépenses mensuelles totales"
        "monthly_expenses" -> "Dépenses mensuelles"
        "total_spent_this_month" -> "Total dépensé ce mois-ci"
        "category_breakdown" -> "Répartition par catégorie"
        "highest_spending_category" -> "Catégorie avec les dépenses les plus élevées"
        "lowest_spending_category" -> "Catégorie avec les dépenses les plus faibles"
        "average_daily_spending" -> "Dépenses quotidiennes moyennes"
        "monthly_savings" -> "Épargne mensuelle"
        "spending_summary" -> "Résumé des dépenses"
        "no_monthly_insights" -> "Aucun aperçu mensuel disponible."
        "monthly_insight_message" -> "Voici un aperçu de vos dépenses mensuelles."
        "no_data_for_month" -> "Aucune donnée disponible pour ce mois."
        "monthly_total" -> "Total mensuel"
        "monthly_average" -> "Moyenne mensuelle"
        "daily_average" -> "Moyenne quotidienne"
        "top_category" -> "Catégorie principale"
        "highest_expense" -> "Dépense la plus élevée"
        "lowest_expense" -> "Dépense la plus faible"
        "expense_trend" -> "Tendance des dépenses"
        "spending_pattern" -> "Modèle de dépenses"
        "compare_months" -> "Comparer les mois"
        "monthly_report" -> "Rapport mensuel"
        "summary" -> "Résumé"

        "smart_financial_decisions" -> "Décisions financières intelligentes"
        "make_smarter_decisions" -> "Prenez de meilleures décisions grâce à vos données financières."
        "what_should_i_cut" -> "Que devrais-je réduire ?"
        "what_if" -> "Et si ?"
        "see_reducing_spending" -> "Découvrez comment réduire vos dépenses peut affecter votre argent disponible."
        "current_available_money" -> "Argent actuellement disponible"
        "available_money" -> "Argent disponible"
        "available_after_suggested_cuts" -> "Argent disponible après les réductions suggérées"
        "smart_suggestions" -> "Suggestions intelligentes"
        "top_suggestions" -> "Principales suggestions"
        "suggestions" -> "Suggestions"
        "recommendations" -> "Recommandations"
        "suggested_action" -> "Action suggérée"
        "potential_savings" -> "Économies potentielles"
        "potential_monthly_savings" -> "Économies mensuelles potentielles"
        "potential_monthly_savings_zero" -> "Économies mensuelles potentielles : {amount}"

        "add_expenses_personalized" -> "Ajoutez d'abord quelques dépenses pour obtenir des suggestions personnalisées."
        "no_major_reduction" -> "Vos dépenses actuelles ne montrent pas de possibilité majeure de réduction."
        "suggestions_current_spending" -> "Les suggestions sont basées sur vos habitudes de dépenses actuelles."

        "reduce_spending" -> "Réduire les dépenses"
        "reduce_category" -> "Réduire les dépenses de la catégorie"
        "save_more" -> "Épargner davantage"
        "spending_habit" -> "Habitude de dépense"
        "financial_decision" -> "Décision financière"

        "cut_shopping" -> "Réduire les dépenses d'achat"
        "cut_food" -> "Réduire les dépenses alimentaires"
        "cut_entertainment" -> "Réduire les dépenses de divertissement"
        "cut_travel" -> "Réduire les dépenses de voyage"
        "cut_bills" -> "Réduire les dépenses de factures"
        "cut_other" -> "Réduire les autres dépenses"

        "simple_actions_financial_situation" -> "Actions simples basées sur votre situation financière."
        "set_aside_available_money" -> "Essayez de mettre de côté une partie de votre argent disponible chaque mois."
        "review_subscriptions" -> "Vérifiez régulièrement vos abonnements et dépenses récurrentes."
        "check_existing_emi" -> "Vérifiez vos engagements EMI actuels avant d'ajouter une autre EMI."
        "use_savings_goal" -> "Utilisez vos économies mensuelles potentielles comme objectif de départ."
        "set_monthly_limits" -> "Définissez des limites mensuelles pour vos catégories."
        "spending_alerts_actual" -> "Alertes de dépenses basées sur vos dépenses réelles."
        "latest_entries" -> "Vos dernières entrées."

        "cut" -> "Réduire"
        "suggested" -> "Suggéré"
        "spent" -> "Dépensé"
        "keep_it_up" -> "Continuez comme ça !"
        "view_summary" -> "Voir le résumé"
        "apply_suggestion" -> "Appliquer la suggestion"

        "date_not_set" -> "Date non définie"
        "enter_valid_amount" -> "Entrez un montant valide."
        "enter_valid_limit" -> "Entrez une limite valide."

        "where_did_you_spend" -> "Où avez-vous dépensé ?"
        "expense_note" -> "Note de dépense"
        "today" -> "Aujourd'hui"
        "yesterday" -> "Hier"

        "back" -> "Retour"

        else -> englishText(key)
    }
}

// ============================================================
// DATA CLASSES
// ============================================================

data class EMIData(
    val id: Int,
    val lender: String,
    val amount: Double,
    val dueDate: String,
    val frequency: String
)

data class EMIResponseData(
    val success: Boolean,
    val userId: Int,
    val name: String,
    val email: String,
    val mobile: String,
    val salary: Double,
    val emiData: List<EMIData>,
    val totalEmi: Double,
    val emiRatio: Double,
    val remainingSalary: Double,
    val status: String
)

data class CountryOption(
    val code: String,
    val name: String
)

data class LanguageOption(
    val code: String,
    val name: String
)

data class CurrencyOption(
    val code: String,
    val name: String,
    val symbol: String
)

data class Expense(
    val id: Int,
    val amount: Double,
    val category: String,
    val date: String,
    val note: String
)

data class PayWiseCurrencyState(
    val code: String,
    val symbol: String,
    val rateFromINR: Double
)

val LocalPayWiseCurrency = staticCompositionLocalOf {
    PayWiseCurrencyState("INR", "₹", 1.0)
}

@Composable
fun formatPayWiseMoney(amount: Double): String {
    val currency = LocalPayWiseCurrency.current
    val convertedAmount = amount * currency.rateFromINR
    return "${currency.symbol} %.2f".format(convertedAmount)
}

private suspend fun fetchINRExchangeRate(targetCurrency: String): Double {
    if (targetCurrency == "INR") return 1.0

    return try {
        val request = Request.Builder()
            .url("https://api.frankfurter.app/latest?from=INR&to=$targetCurrency")
            .get()
            .build()

        val response = withContext(Dispatchers.IO) {
            OkHttpClient().newCall(request).execute()
        }

        if (!response.isSuccessful) return 1.0

        val body = response.body?.string() ?: return 1.0
        val json = JsonParser.parseString(body).asJsonObject
        json.getAsJsonObject("rates")
            ?.get(targetCurrency)
            ?.asDouble
            ?: 1.0
    } catch (_: Exception) {
        1.0
    }
}

// ============================================================
// MAIN ACTIVITY
// ============================================================

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PayWiseApp()
        }
    }
}

// ============================================================
// MAIN APP
// ============================================================

@Composable
fun PayWiseApp() {

    var screen by rememberSaveable {
        mutableStateOf("login")
    }

    var userId by rememberSaveable {
        mutableStateOf(0)
    }

    var userName by rememberSaveable {
        mutableStateOf("")
    }

    var userEmail by rememberSaveable {
        mutableStateOf("")
    }

    var userMobile by rememberSaveable {
        mutableStateOf("")
    }

    // ========================================================
    // GLOBAL LANGUAGE
    // ========================================================

    var selectedLanguageCode by rememberSaveable {
        mutableStateOf("en")
    }

    var selectedCurrencyCode by rememberSaveable {
        mutableStateOf("INR")
    }

    var currencyRate by rememberSaveable {
        mutableStateOf(1.0)
    }

    androidx.compose.runtime.LaunchedEffect(selectedCurrencyCode) {
        currencyRate = fetchINRExchangeRate(selectedCurrencyCode)
    }

    val selectedCurrencySymbol = remember(selectedCurrencyCode) {
        try {
            Currency.getInstance(selectedCurrencyCode)
                .getSymbol(Locale.ENGLISH)
        } catch (_: Exception) {
            selectedCurrencyCode
        }
    }

    CompositionLocalProvider(
        LocalPayWiseCurrency provides PayWiseCurrencyState(
            code = selectedCurrencyCode,
            symbol = selectedCurrencySymbol,
            rateFromINR = currencyRate
        )
    ) {

        when (screen) {

            // ====================================================
            // LOGIN
            // ====================================================

            "login" -> {

                LoginScreen(
                    languageCode = selectedLanguageCode,

                    onLoginSuccess = { id, name, email, mobile ->

                        userId = id
                        userName = name
                        userEmail = email
                        userMobile = mobile

                        screen = "dashboard"
                    },

                    onSignup = {
                        screen = "signup"
                    }
                )
            }

            // ====================================================
            // SIGNUP
            // ====================================================

            "signup" -> {

                SignupScreen(
                    languageCode = selectedLanguageCode,

                    onSignupSuccess = {
                        screen = "login"
                    },

                    onBack = {
                        screen = "login"
                    }
                )
            }

            // ====================================================
            // DASHBOARD
            // ====================================================

            "dashboard" -> {

                DashboardScreen(
                    userId = userId,
                    userName = userName,
                    userEmail = userEmail,
                    userMobile = userMobile,
                    languageCode = selectedLanguageCode,
                    selectedCurrencyCode = selectedCurrencyCode,

                    onLanguageChanged = {
                        selectedLanguageCode = it
                    },

                    onCurrencyChanged = {
                        selectedCurrencyCode = it
                    },

                    onProfileUpdated = {
                            updatedName,
                            updatedEmail,
                            updatedMobile ->

                        userName = updatedName
                        userEmail = updatedEmail
                        userMobile = updatedMobile
                    },

                    onLogout = {
                        userId = 0
                        userName = ""
                        userEmail = ""
                        userMobile = ""
                        screen = "login"
                    }
                )
            }
        }
    }
}


// ============================================================
// LOGIN
// ============================================================

@Composable
fun LoginScreen(
    languageCode: String,
    onLoginSuccess: (Int, String, String, String) -> Unit,
    onSignup: () -> Unit
) {

    var email by rememberSaveable {
        mutableStateOf("")
    }

    var password by rememberSaveable {
        mutableStateOf("")
    }

    var message by rememberSaveable {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundWhite)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = tr("paywise", languageCode),
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryBlue
        )

        Text(
            text = tr("tagline", languageCode),
            fontSize = 16.sp,
            color = GreyText
        )

        Spacer(modifier = Modifier.height(35.dp))

        Text(
            text = tr("login", languageCode),
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = DeepBlue
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(tr("email", languageCode))
            },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(tr("password", languageCode))
            },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {

                if (email.isBlank() || password.isBlank()) {

                    message = tr(
                        "please_enter_login",
                        languageCode
                    )

                    return@Button
                }

                loginUser(
                    email = email,
                    password = password,

                    onSuccess = { id, name, userEmail, mobile ->

                        onLoginSuccess(
                            id,
                            name,
                            userEmail,
                            mobile
                        )
                    },

                    onError = {
                        message = it
                    }
                )
            },

            modifier = Modifier.fillMaxWidth(),

            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryBlue
            )
        ) {

            Text(
                text = tr("login", languageCode),
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        TextButton(
            onClick = onSignup
        ) {

            Text(
                text = tr(
                    "signup",
                    languageCode
                ),
                color = Orange
            )
        }

        if (message.isNotBlank()) {

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = message,
                color = ErrorRed,
                textAlign = TextAlign.Center
            )
        }
    }
}


// ============================================================
// SIGNUP
// ============================================================

@Composable
fun SignupScreen(
    languageCode: String,
    onSignupSuccess: () -> Unit,
    onBack: () -> Unit
) {

    var name by rememberSaveable {
        mutableStateOf("")
    }

    var email by rememberSaveable {
        mutableStateOf("")
    }

    var mobile by rememberSaveable {
        mutableStateOf("")
    }

    var password by rememberSaveable {
        mutableStateOf("")
    }

    var confirmPassword by rememberSaveable {
        mutableStateOf("")
    }

    var message by rememberSaveable {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundWhite)
            .padding(24.dp)
    ) {

        Text(
            text = tr("paywise", languageCode),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryBlue
        )

        Text(
            text = tr(
                "create_your_account",
                languageCode
            ),
            fontSize = 16.sp,
            color = GreyText
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    tr(
                        "full_name",
                        languageCode
                    )
                )
            },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    tr(
                        "email",
                        languageCode
                    )
                )
            },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = mobile,
            onValueChange = {
                mobile = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    tr(
                        "mobile",
                        languageCode
                    )
                )
            },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    tr(
                        "password",
                        languageCode
                    )
                )
            },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    tr(
                        "confirm_password",
                        languageCode
                    )
                )
            },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {

                if (
                    name.isBlank() ||
                    email.isBlank() ||
                    mobile.isBlank() ||
                    password.isBlank()
                ) {

                    message = tr(
                        "please_fill_all",
                        languageCode
                    )

                    return@Button
                }

                if (password != confirmPassword) {

                    message = tr(
                        "passwords_not_match",
                        languageCode
                    )

                    return@Button
                }

                signupUser(
                    name = name,
                    email = email,
                    mobile = mobile,
                    password = password,

                    onSuccess = {
                        onSignupSuccess()
                    },

                    onError = {
                        message = it
                    }
                )
            },

            modifier = Modifier.fillMaxWidth(),

            colors = ButtonDefaults.buttonColors(
                containerColor = Orange
            )
        ) {

            Text(
                text = tr(
                    "create_account",
                    languageCode
                ),
                color = Color.White
            )
        }

        TextButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = tr(
                    "back_to_login",
                    languageCode
                ),
                color = PrimaryBlue
            )
        }

        if (message.isNotBlank()) {

            Text(
                text = message,
                color = ErrorRed,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


// ============================================================
// DASHBOARD
// ============================================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    userId: Int,
    userName: String,
    userEmail: String,
    userMobile: String,
    languageCode: String,
    selectedCurrencyCode: String,
    onLanguageChanged: (String) -> Unit,
    onCurrencyChanged: (String) -> Unit,

    // Profile updates are sent back to PayWiseApp.
    onProfileUpdated: (String, String, String) -> Unit,

    onLogout: () -> Unit
) {

    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var selectedPage by rememberSaveable {
        mutableStateOf("Dashboard")
    }

    // Payment reminder preference used by both Dashboard and Settings.
    var reminderDays by rememberSaveable {
        mutableStateOf(3)
    }

    var emiResponse by remember {
        mutableStateOf<EMIResponseData?>(null)
    }

    var loading by remember {
        mutableStateOf(true)
    }

    var message by remember {
        mutableStateOf("")
    }

    // Everyday Expenses data is kept per logged-in user
    // during the current app session.

    var expenses by remember {
        mutableStateOf(
            MonthlyExpenseStorage.loadExpenses(context)
        )
    }

    var monthlyInsights by remember {
        mutableStateOf(
            MonthlyExpenseStorage.loadMonthlyInsights(context)
        )
    }

    var spendingLimits by remember {
        mutableStateOf(
            MonthlyExpenseStorage.loadSpendingLimits(context)
        )
    }

    fun loadData() {

        if (userId == 0) return

        loading = true

        getEMIData(
            userId = userId,

            onSuccess = {
                emiResponse = it
                loading = false
                message = ""

                EMIReminderScheduler.scheduleAllReminders(
                    context = context,
                    emiList = it.emiData,
                    reminderDays = reminderDays
                )

                it.emiData.forEach { emi ->
                    EMIRecurringReminderScheduler.scheduleRecurringReminder(
                        context = context,
                        emi = emi,
                        reminderDays = reminderDays
                    )
                }
            },

            onError = {
                loading = false
                message = it
            }
        )
    }

    androidx.compose.runtime.LaunchedEffect(userId) {
        loadData()
    }

    androidx.compose.runtime.LaunchedEffect(Unit) {

        val preferences =
            context.getSharedPreferences(
                "paywise_monthly_data",
                Context.MODE_PRIVATE
            )

        val currentMonth =
            SimpleDateFormat(
                "yyyy-MM",
                Locale.getDefault()
            ).format(Date())

        val savedMonth =
            preferences.getString(
                "current_month",
                ""
            )

        if (savedMonth.isNullOrEmpty()) {

            preferences.edit()
                .putString(
                    "current_month",
                    currentMonth
                )
                .apply()

        } else if (savedMonth != currentMonth) {

            val previousMonthExpenses =
                expenses

            if (previousMonthExpenses.isNotEmpty()) {

                val previousCategoryTotals =
                    expenseCategories.associateWith { category ->
                        previousMonthExpenses
                            .filter {
                                it.category == category
                            }
                            .sumOf {
                                it.amount
                            }
                    }

                val previousTotal =
                    previousMonthExpenses.sumOf {
                        it.amount
                    }

                val previousMonthName =
                    SimpleDateFormat(
                        "MMMM yyyy",
                        Locale.getDefault()
                    ).format(
                        SimpleDateFormat(
                            "yyyy-MM",
                            Locale.getDefault()
                        ).parse(savedMonth)
                            ?: Date()
                    )

                monthlyInsights =
                    monthlyInsights +
                            MonthlyInsight(
                                month = previousMonthName,
                                totalSpent = previousTotal,
                                categoryTotals =
                                    previousCategoryTotals
                            )
            }

            expenses = emptyList()

            spendingLimits = emptyMap()

            preferences.edit()
                .putString(
                    "current_month",
                    currentMonth
                )
                .apply()
        }
    }

    val pageTitle = when (selectedPage) {

        "Dashboard" ->
            tr("dashboard", languageCode)

        "Add Salary" ->
            tr("add_salary", languageCode)

        "My EMIs" ->
            tr("my_emis", languageCode)

        "Add EMI" ->
            tr("add_emi", languageCode)

        "Financial Summary" ->
            tr("financial_summary", languageCode)

        "Everyday Expenses" ->
            tr("everyday_expenses", languageCode)

        "Monthly Insights" ->
            tr("monthly_insights", languageCode)

        "Smart Financial Decisions" ->
            tr("smart_financial_decisions", languageCode)

        "Profile" ->
            tr("profile", languageCode)

        "Settings" ->
            tr("settings", languageCode)

        else ->
            tr("dashboard", languageCode)
    }

    ModalNavigationDrawer(

        drawerState = drawerState,

        drawerContent = {

            ModalDrawerSheet(
                drawerContainerColor = BackgroundWhite
            ) {

                Spacer(
                    modifier = Modifier.height(35.dp)
                )

                Text(
                    text = tr(
                        "paywise",
                        languageCode
                    ),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryBlue,
                    modifier = Modifier.padding(
                        horizontal = 20.dp
                    )
                )

                Text(
                    text = tr(
                        "tagline",
                        languageCode
                    ),
                    color = GreyText,
                    modifier = Modifier.padding(
                        horizontal = 20.dp
                    )
                )

                Spacer(
                    modifier = Modifier.height(25.dp)
                )

                HorizontalDivider()

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                val menuItems = listOf(
                    "Dashboard",
                    "Add Salary",
                    "My EMIs",
                    "Add EMI",
                    "Financial Summary",
                    "Everyday Expenses",
                    "Monthly Insights",
                    "Smart Financial Decisions",
                    "Profile",
                    "Settings"
                )

                menuItems.forEach { item ->

                    val translatedItem = when (item) {

                        "Dashboard" ->
                            tr("dashboard", languageCode)

                        "Add Salary" ->
                            tr("add_salary", languageCode)

                        "My EMIs" ->
                            tr("my_emis", languageCode)

                        "Add EMI" ->
                            tr("add_emi", languageCode)

                        "Financial Summary" ->
                            tr("financial_summary", languageCode)

                        "Everyday Expenses" ->
                            tr("everyday_expenses", languageCode)

                        "Monthly Insights" ->
                            tr("monthly_insights", languageCode)

                        "Smart Financial Decisions" ->
                            tr("smart_financial_decisions", languageCode)

                        "Profile" ->
                            tr("profile", languageCode)

                        "Settings" ->
                            tr("settings", languageCode)

                        else ->
                            item
                    }.toString()

                    NavigationDrawerItem(
                        label = {
                            Text(
                                text = translatedItem,
                                fontWeight =
                                    if (selectedPage == item) {
                                        FontWeight.Bold
                                    } else {
                                        FontWeight.Normal
                                    }
                            )
                        },
                        selected = selectedPage == item,
                        onClick = {
                            selectedPage = item
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        colors =
                            NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = LightBlue,
                                selectedTextColor = DeepBlue,
                                unselectedTextColor = DarkText
                            ),
                        modifier = Modifier.padding(
                            horizontal = 10.dp,
                            vertical = 2.dp
                        )
                    )
                }

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                HorizontalDivider()

                NavigationDrawerItem(

                    label = {

                        Text(
                            text = tr(
                                "logout",
                                languageCode
                            ),
                            color = ErrorRed
                        )
                    },

                    selected = false,

                    onClick = {

                        scope.launch {
                            drawerState.close()
                        }

                        onLogout()
                    },

                    modifier = Modifier.padding(
                        horizontal = 10.dp
                    )
                )
            }
        }
    ) {

        Scaffold(

            topBar = {

                TopAppBar(

                    title = {

                        Text(
                            text = pageTitle,
                            fontWeight = FontWeight.Bold
                        )
                    },

                    navigationIcon = {

                        IconButton(
                            onClick = {

                                scope.launch {

                                    if (drawerState.isClosed) {
                                        drawerState.open()
                                    } else {
                                        drawerState.close()
                                    }
                                }
                            }
                        ) {

                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu",
                                tint = Color.White
                            )
                        }
                    },

                    colors =
                        TopAppBarDefaults.topAppBarColors(
                            containerColor = PrimaryBlue,
                            titleContentColor = Color.White
                        )
                )
            }
        ) { paddingValues ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(BackgroundWhite)
            ) {

                when (selectedPage) {

                    // ==================================================
                    // DASHBOARD
                    // ==================================================

                    "Dashboard" -> {

                        DashboardHome(
                            userName = userName,
                            emiResponse = emiResponse,
                            loading = loading,
                            message = message,
                            languageCode = languageCode,
                            reminderDays = reminderDays,
                            onReminderDaysChanged = {
                                reminderDays = it
                            }
                        )
                    }

                    // ==================================================
                    // ADD SALARY
                    // ==================================================

                    "Add Salary" -> {

                        AddSalaryScreen(
                            userId = userId,
                            languageCode = languageCode,

                            onUpdated = {
                                loadData()
                                selectedPage = "Dashboard"
                            }
                        )
                    }

                    // ==================================================
                    // MY EMIs
                    // ==================================================

                    "My EMIs" -> {

                        MyEMIsScreen(
                            emiData =
                                emiResponse?.emiData
                                    ?: emptyList(),

                            languageCode = languageCode
                        )
                    }

                    // ==================================================
                    // ADD EMI
                    // ==================================================

                    "Add EMI" -> {

                        AddEMIScreen(
                            userId = userId,
                            languageCode = languageCode,

                            onAdded = {
                                loadData()
                                selectedPage = "My EMIs"
                            }
                        )
                    }

                    // ==================================================
                    // FINANCIAL SUMMARY
                    // ==================================================

                    "Financial Summary" -> {

                        FinancialSummaryScreen(
                            response = emiResponse,
                            languageCode = languageCode
                        )
                    }

                    // ==================================================
                    // EVERYDAY EXPENSES
                    // ==================================================

                    "Everyday Expenses" -> {

                        EverydayExpensesScreen(
                            expenses = expenses,
                            spendingLimits = spendingLimits,

                            salary =
                                emiResponse?.salary ?: 0.0,

                            totalEmi =
                                emiResponse?.totalEmi ?: 0.0,

                            languageCode = languageCode,

                            onAddExpense = { expense ->
                                expenses = expenses + expense

                                MonthlyExpenseStorage.saveExpenses(
                                    context = context,
                                    expenses = expenses
                                )
                            },

                            onSetLimit = { category, limit ->

                                spendingLimits =
                                    spendingLimits + (category to limit)

                                MonthlyExpenseStorage.saveSpendingLimits(
                                    context = context,
                                    limits = spendingLimits
                                )
                            }
                        )
                    }

                    "Monthly Insights" -> {
                        MonthlyInsightsScreen(
                            monthlyInsights = monthlyInsights,
                            languageCode = languageCode
                        )
                    }

                    "Smart Financial Decisions" -> {
                        SmartFinancialDecisionsScreen(
                            languageCode = languageCode,
                            onWhatIfClick = {
                                selectedPage = "What If?"
                            },
                            onFinancialHealthTipsClick = {
                                selectedPage = "Financial Health Tips"
                            },
                            expenses = expenses
                        )
                    }

                    "What If?" -> {
                        WhatIfScreen(
                            languageCode = languageCode
                        )
                    }

                    "Financial Health Tips" -> {
                        FinancialHealthTipsScreen(
                            languageCode = languageCode
                        )
                    }

                    // ==================================================
                    // PROFILE
                    // ==================================================

                    "Profile" -> {

                        ProfileScreen(
                            userId = userId,
                            userName = userName,
                            userEmail = userEmail,
                            userMobile = userMobile,
                            emiResponse = emiResponse,
                            languageCode = languageCode,

                            onProfileUpdated = {
                                    updatedName,
                                    updatedEmail,
                                    updatedMobile ->

                                // Send the changes back to PayWiseApp.
                                onProfileUpdated(
                                    updatedName,
                                    updatedEmail,
                                    updatedMobile
                                )
                            }
                        )
                    }

                    // ==================================================
                    // SETTINGS
                    // ==================================================


                    "Settings" -> {
                        SettingsScreen(
                            selectedLanguageCode = languageCode,
                            selectedCurrencyCode = selectedCurrencyCode,
                            selectedReminderDays = reminderDays,

                            onLanguageChanged = {
                                onLanguageChanged(it)
                            },

                            onCurrencyChanged = {
                                onCurrencyChanged(it)
                            },

                            onReminderDaysChanged = {
                                reminderDays = it

                                emiResponse?.let { data ->


                                    EMIReminderScheduler.scheduleAllReminders(
                                        context = context,
                                        emiList = data.emiData,
                                        reminderDays = it
                                    )

                                    data.emiData.forEach { emi ->
                                        EMIRecurringReminderScheduler.scheduleRecurringReminder(
                                            context = context,
                                            emi = emi,
                                            reminderDays = it
                                        )
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

// ============================================================
// DASHBOARD HOME
// ============================================================

@Composable
fun DashboardHome(
    userName: String,
    emiResponse: EMIResponseData?,
    loading: Boolean,
    message: String,
    languageCode: String,
    reminderDays: Int,
    onReminderDaysChanged: (Int) -> Unit
) {

    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        item {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = LightBlue
                ),
                shape = RoundedCornerShape(18.dp)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp)
                ) {
                    Text(
                        text =
                            "${tr("welcome", languageCode)}, $userName",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = DeepBlue
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = tr(
                            "manage_finances",
                            languageCode
                        ),
                        color = GreyText
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )
        }

        if (loading) {

            item {

                Text(
                    text = tr(
                        "loading_financial_data",
                        languageCode
                    ),
                    color = GreyText
                )

                Spacer(
                    modifier = Modifier.height(15.dp)
                )
            }
        }

        if (message.isNotBlank()) {

            item {

                Text(
                    text = message,
                    color = ErrorRed
                )

                Spacer(
                    modifier = Modifier.height(15.dp)
                )
            }
        }

        emiResponse?.let { data ->

            item {
                Text(
                    text = "Financial Overview",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = DeepBlue,
                    modifier = Modifier.padding(vertical = 6.dp)
                )
            }

            item {

                FinancialCard(
                    title = tr(
                        "monthly_salary",
                        languageCode
                    ),
                    value =
                        formatPayWiseMoney(data.salary),
                    backgroundColor = LightBlue
                )
            }

            item {

                FinancialCard(
                    title = tr(
                        "total_emi",
                        languageCode
                    ),
                    value =
                        formatPayWiseMoney(data.totalEmi),
                    backgroundColor =
                        Color(0xFFFFF3E0)
                )
            }

            item {

                FinancialCard(
                    title = tr(
                        "remaining_salary",
                        languageCode
                    ),
                    value =
                        formatPayWiseMoney(data.remainingSalary),
                    backgroundColor =
                        Color(0xFFE3F2FD)
                )
            }

            item {

                FinancialCard(
                    title = tr(
                        "emi_ratio",
                        languageCode
                    ),
                    value =
                        "%.2f%%".format(
                            data.emiRatio
                        ),
                    backgroundColor =
                        Color(0xFFFFF8E1)
                )
            }

            item {

                PaymentReminderCard(
                    emiData = data.emiData,
                    reminderDays = reminderDays,
                    languageCode = languageCode,
                    onReminderDaysChanged = onReminderDaysChanged
                )
            }

                    item {

                Button(
                    onClick = {
                        data.emiData.firstOrNull()?.let { emi ->

                            EMIRecurringReminderScheduler.scheduleDemoReminder(
                                context = context,
                                emi = emi,
                                delayMinutes = 1
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = "Test Notification"
                    )
                }
            }

            item {

                StatusCard(
                    status = data.status,
                    languageCode = languageCode,
                    dashboardStyle = true
                )
            }

            item {

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Text(
                    text = tr(
                        "your_emis",
                        languageCode
                    ),
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold,
                    color = DeepBlue
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )
            }

            items(data.emiData) { emi ->

                EMICard(
                    emi = emi,
                    languageCode = languageCode,
                    dashboardStyle = true
                )
            }
        }
    }
}

// ============================================================
// PAYMENT REMINDER CARD
// ============================================================

@Composable
fun PaymentReminderCard(
    emiData: List<EMIData>,
    reminderDays: Int,
    languageCode: String,
    onReminderDaysChanged: (Int) -> Unit
) {

    val context = LocalContext.current

    val nextEmi = remember(emiData) {
        emiData
            .mapNotNull { emi ->
                try {
                    val parts = emi.dueDate.trim().split("/")

                    if (parts.size == 3) {
                        val day = parts[0].toInt()
                        val month = parts[1].toInt()
                        val year = parts[2].toInt()

                        val calendar = java.util.Calendar.getInstance().apply {
                            set(
                                java.util.Calendar.YEAR,
                                year
                            )
                            set(
                                java.util.Calendar.MONTH,
                                month - 1
                            )
                            set(
                                java.util.Calendar.DAY_OF_MONTH,
                                day
                            )
                            set(
                                java.util.Calendar.HOUR_OF_DAY,
                                0
                            )
                            set(
                                java.util.Calendar.MINUTE,
                                0
                            )
                            set(
                                java.util.Calendar.SECOND,
                                0
                            )
                            set(
                                java.util.Calendar.MILLISECOND,
                                0
                            )
                        }

                        calendar.time to emi
                    } else {
                        null
                    }
                } catch (_: Exception) {
                    null
                }
            }
            .minByOrNull { it.first }
    }

    val daysUntilDue = nextEmi?.let {
        val today = java.util.Calendar.getInstance().apply {
            set(
                java.util.Calendar.HOUR_OF_DAY,
                0
            )
            set(
                java.util.Calendar.MINUTE,
                0
            )
            set(
                java.util.Calendar.SECOND,
                0
            )
            set(
                java.util.Calendar.MILLISECOND,
                0
            )
        }

        val dueDate = java.util.Calendar.getInstance().apply {
            time = it.first
            set(
                java.util.Calendar.HOUR_OF_DAY,
                0
            )
            set(
                java.util.Calendar.MINUTE,
                0
            )
            set(
                java.util.Calendar.SECOND,
                0
            )
            set(
                java.util.Calendar.MILLISECOND,
                0
            )
        }

        val differenceMillis =
            dueDate.timeInMillis - today.timeInMillis

        (
                differenceMillis /
                        (24 * 60 * 60 * 1000L)
                ).toInt()
    }

    val reminderText = if (nextEmi == null) {
        "No valid EMI due date available"
    } else {
        when {
            daysUntilDue!! < 0 ->
                "EMI was due on ${nextEmi.second.dueDate}"

            daysUntilDue == 0 ->
                "EMI is due today"

            daysUntilDue <= reminderDays ->
                "Reminder: ${nextEmi.second.lender} is due in $daysUntilDue day(s)"

            else ->
                "Reminder will appear $reminderDays days before the due date"
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFF8E1)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            Text(
                text = "Payment Reminder",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = DeepBlue
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = reminderText,
                color = DarkText,
                fontSize = 14.sp
            )

            if (nextEmi != null) {
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "${nextEmi.second.lender} • ${
                        formatPayWiseMoney(nextEmi.second.amount)
                    } • Due ${nextEmi.second.dueDate}",
                    color = GreyText,
                    fontSize = 13.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Remind me",
                color = DarkText,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                listOf(1, 3, 5, 7).forEach { days ->

                    Button(
                        onClick = {
                            onReminderDaysChanged(days)
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor =
                                if (reminderDays == days) {
                                    Orange
                                } else {
                                    LightBlue
                                },
                            contentColor =
                                if (reminderDays == days) {
                                    Color.White
                                } else {
                                    DeepBlue
                                }
                        ),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(
                            horizontal = 4.dp,
                            vertical = 8.dp
                        )
                    ) {
                        Text("${days}d")
                    }
                }
            }
        }
    }
}
// ============================================================
// FINANCIAL CARD
// ============================================================

@Composable
fun FinancialCard(
    title: String,
    value: String,
    backgroundColor: Color
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 6.dp
            ),

        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),

        shape = RoundedCornerShape(16.dp)
    ) {

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            Text(
                text = title,
                color = GreyText,
                fontSize = 14.sp
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            Text(
                text = value,
                color = DarkText,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// ============================================================
// STATUS CARD
// ============================================================

@Composable
fun StatusCard(
    status: String,
    languageCode: String,
    dashboardStyle: Boolean = false
) {

    val displayStatus = status
        .trim()
        .uppercase()

    val statusColor = if (dashboardStyle) {
        when (displayStatus) {
            "SAFE" -> SuccessGreen
            "MODERATE" -> Yellow
            "HIGH" -> Orange
            "VERY HIGH" -> ErrorRed
            else -> PrimaryBlue
        }
    } else {
        when (displayStatus) {
            "SAFE" -> SuccessGreen
            "MODERATE" -> Yellow
            "HIGH" -> Orange
            "VERY HIGH" -> ErrorRed
            else -> PrimaryBlue
        }
    }

    val translatedStatus = when (displayStatus) {

        "SAFE" -> when (languageCode) {
            "hi" -> "सुरक्षित"
            "mr" -> "सुरक्षित"
            "es" -> "SEGURO"
            else -> "SAFE"
        }

        "MODERATE" -> when (languageCode) {
            "hi" -> "मध्यम"
            "mr" -> "मध्यम"
            "es" -> "MODERADO"
            else -> "MODERATE"
        }

        "HIGH" -> when (languageCode) {
            "hi" -> "उच्च"
            "mr" -> "जास्त"
            "es" -> "ALTO"
            else -> "HIGH"
        }

        "VERY HIGH" -> when (languageCode) {
            "hi" -> "बहुत अधिक"
            "mr" -> "खूप जास्त"
            "es" -> "MUY ALTO"
            else -> "VERY HIGH"
        }

        else -> when (languageCode) {
            "hi" -> "उपलब्ध नहीं"
            "mr" -> "उपलब्ध नाही"
            "es" -> "NO DISPONIBLE"
            else -> "NOT AVAILABLE"
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 6.dp
            ),

        colors = CardDefaults.cardColors(
            containerColor = statusColor
        ),

        shape = RoundedCornerShape(16.dp)
    ) {

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            Text(
                text = tr(
                    "financial_status",
                    languageCode
                ),
                color = Color.White,
                fontSize = 14.sp
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            Text(
                text = translatedStatus,
                color = Color.White,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// ============================================================
// EMI CARD
// ============================================================

@Composable
fun EMICard(
    emi: EMIData,
    languageCode: String,
    dashboardStyle: Boolean = false
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 6.dp
            ),

        colors = CardDefaults.cardColors(
            containerColor = if (dashboardStyle) {
                when (kotlin.math.abs(emi.lender.hashCode()) % 3) {
                    0 -> LightBlue
                    1 -> Color(0xFFFFF3E0)
                    else -> Color(0xFFFFF8E1)
                }
            } else {
                when (kotlin.math.abs(emi.lender.hashCode()) % 5) {
                    0 -> Color(0xFFE3F2FD)
                    1 -> Color(0xFFFFE0B2)
                    2 -> Color(0xFFFFF3CD)
                    3 -> Color(0xFFE8F5E9)
                    else -> Color(0xFFFFEBEE)
                }
            }
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = emi.lender,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = DeepBlue
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            Text(
                text =
                    "${tr("emi_amount", languageCode)}: ${formatPayWiseMoney(emi.amount)}",
                color = DarkText
            )

            Text(
                text =
                    "${tr("due_date", languageCode)}: ${emi.dueDate}",
                color = GreyText
            )

            Text(
                text =
                    "${tr("frequency", languageCode)}: " +
                            if (
                                emi.frequency.equals(
                                    "Monthly",
                                    ignoreCase = true
                                )
                            ) {
                                tr(
                                    "monthly",
                                    languageCode
                                )
                            } else {
                                emi.frequency
                            },
                color = GreyText
            )
        }
    }
}

// ============================================================
// ADD SALARY
// ============================================================

@Composable
fun AddSalaryScreen(
    userId: Int,
    languageCode: String,
    onUpdated: () -> Unit
) {

    val currency = LocalPayWiseCurrency.current

    var salary by rememberSaveable {
        mutableStateOf("")
    }

    var message by rememberSaveable {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = tr(
                "add_update_salary",
                languageCode
            ),
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = DeepBlue
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        OutlinedTextField(
            value = salary,
            onValueChange = {
                salary = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    "${tr("monthly_salary", languageCode)} (${currency.symbol})"
                )
            },
            singleLine = true
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Button(
            onClick = {

                val salaryValue =
                    salary.toDoubleOrNull()

                if (salaryValue == null) {

                    message =
                        tr(
                            "enter_valid_salary",
                            languageCode
                        )

                    return@Button
                }

                updateSalary(
                    userId = userId,
                    // Backend stores financial values in INR.
                    salary = salaryValue / currency.rateFromINR,

                    onSuccess = {

                        message =
                            tr(
                                "salary_updated",
                                languageCode
                            )

                        onUpdated()
                    },

                    onError = {
                        message = it
                    }
                )
            },

            modifier = Modifier.fillMaxWidth(),

            colors = ButtonDefaults.buttonColors(
                containerColor = Orange
            )
        ) {

            Text(
                text = tr(
                    "save_salary",
                    languageCode
                ),
                color = Color.White
            )
        }

        if (message.isNotBlank()) {

            Spacer(
                modifier = Modifier.height(15.dp)
            )

            Text(
                text = message,
                color =
                    if (
                        message.contains(
                            "success",
                            true
                        )
                    ) {
                        SuccessGreen
                    } else {
                        ErrorRed
                    }
            )
        }
    }
}

// ============================================================
// MY EMIS
// ============================================================

@Composable
fun MyEMIsScreen(
    emiData: List<EMIData>,
    languageCode: String
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        item {

            Text(
                text = tr(
                    "my_emis",
                    languageCode
                ),
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = DeepBlue
            )

            Spacer(
                modifier = Modifier.height(15.dp)
            )
        }

        if (emiData.isEmpty()) {

            item {

                Text(
                    text = tr(
                        "no_emi_records",
                        languageCode
                    ),
                    color = GreyText
                )
            }

        } else {

            items(emiData) { emi ->

                EMICard(
                    emi = emi,
                    languageCode = languageCode
                )
            }
        }
    }
}

// ============================================================
// ADD EMI
// ============================================================

@Composable
fun AddEMIScreen(
    userId: Int,
    languageCode: String,
    onAdded: () -> Unit
) {

    val currency = LocalPayWiseCurrency.current

    var lender by rememberSaveable {
        mutableStateOf("")
    }

    var amount by rememberSaveable {
        mutableStateOf("")
    }

    var dueDate by rememberSaveable {
        mutableStateOf("")
    }

    var frequency by rememberSaveable {
        mutableStateOf("Monthly")
    }

    var message by rememberSaveable {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = tr(
                "add_emi",
                languageCode
            ),
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = DeepBlue
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        OutlinedTextField(
            value = lender,
            onValueChange = {
                lender = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    tr(
                        "bank_lender",
                        languageCode
                    )
                )
            },
            singleLine = true
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        OutlinedTextField(
            value = amount,
            onValueChange = {
                amount = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("${tr("emi_amount", languageCode)} (${currency.symbol})")
            },
            singleLine = true
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        OutlinedTextField(
            value = dueDate,
            onValueChange = {
                dueDate = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    tr(
                        "due_date",
                        languageCode
                    )
                )
            },
            placeholder = {
                Text("DD/MM/YYYY")
            },
            singleLine = true
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        OutlinedTextField(
            value = frequency,
            onValueChange = {
                frequency = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    tr(
                        "frequency",
                        languageCode
                    )
                )
            },
            singleLine = true
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Button(
            onClick = {

                val amountValue =
                    amount.toDoubleOrNull()

                if (
                    lender.isBlank() ||
                    dueDate.isBlank() ||
                    amountValue == null
                ) {

                    message =
                        tr(
                            "valid_emi",
                            languageCode
                        )

                    return@Button
                }

                addEMI(
                    userId = userId,
                    lender = lender,
                    // Backend stores EMI values in INR.
                    amount = amountValue / currency.rateFromINR,
                    dueDate = dueDate,
                    frequency = frequency,

                    onSuccess = {

                        message =
                            tr(
                                "emi_added",
                                languageCode
                            )

                        onAdded()
                    },

                    onError = {
                        message = it
                    }
                )
            },

            modifier = Modifier.fillMaxWidth(),

            colors = ButtonDefaults.buttonColors(
                containerColor = Orange
            )
        ) {

            Text(
                text = tr(
                    "add_emi",
                    languageCode
                ),
                color = Color.White
            )
        }

        if (message.isNotBlank()) {

            Spacer(
                modifier = Modifier.height(15.dp)
            )

            Text(
                text = message,
                color =
                    if (
                        message.contains(
                            "success",
                            true
                        )
                    ) {
                        SuccessGreen
                    } else {
                        ErrorRed
                    }
            )
        }
    }
}

// ============================================================
// EVERYDAY EXPENSES
// ============================================================

val expenseCategories = listOf(
    "Food",
    "Travel",
    "Shopping",
    "Bills",
    "Entertainment",
    "Other"
)

fun expenseCategoryKey(category: String): String {
    return when (category) {
        "Food" -> "food"
        "Travel" -> "travel"
        "Shopping" -> "shopping"
        "Bills" -> "bills"
        "Entertainment" -> "entertainment"
        else -> "other"
    }
}

@Composable
fun EverydayExpensesScreen(
    expenses: List<Expense>,
    spendingLimits: Map<String, Double>,
    salary: Double,
    totalEmi: Double,
    languageCode: String,
    onAddExpense: (Expense) -> Unit,
    onSetLimit: (String, Double) -> Unit
) {
    var showAddExpense by remember { mutableStateOf(false) }
    var limitCategory by remember { mutableStateOf<String?>(null) }

    val totalSpent = expenses.sumOf { it.amount }
    val categoryTotals = expenseCategories.associateWith { category ->
        expenses.filter { it.category == category }.sumOf { it.amount }
    }

    val suggestions = categoryTotals
        .filter { it.value > 500.0 }
        .map { (category, amount) ->
            val cutPercent = when {
                amount >= 2000.0 -> 0.25
                amount >= 1000.0 -> 0.15
                else -> 0.10
            }
            category to kotlin.math.round(amount * cutPercent)
        }
        .filter { it.second >= 50.0 }
        .sortedByDescending { it.second }

    val potentialSavings = suggestions.sumOf { it.second }
    val availableMoney = salary - totalEmi - totalSpent

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = tr("everyday_expenses", languageCode),
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = DeepBlue
            )
            Text(
                text = tr("track_daily_spending", languageCode),
                color = GreyText,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        item {
            Button(
                onClick = { showAddExpense = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
            ) {
                Text(tr("add_expense", languageCode), color = Color.White)
            }
        }

        item {
            ExpenseCategoryCards(
                categoryTotals = categoryTotals,
                languageCode = languageCode
            )
        }

        item {
            ExpenseOverviewCard(
                totalSpent = totalSpent,
                categoryTotals = categoryTotals,
                languageCode = languageCode
            )
        }

        item {
            ExpenseSectionCard(
                title = tr("what_should_i_cut", languageCode),
                subtitle = tr("potential_monthly_savings", languageCode) +
                        ": ${formatPayWiseMoney(potentialSavings)}",
                content = {
                    if (suggestions.isEmpty()) {
                        Text(
                            text = tr("no_expenses", languageCode),
                            color = GreyText
                        )
                    } else {
                        suggestions.forEach { (category, cut) ->
                            ExpenseSuggestionRow(
                                category = category,
                                spent = categoryTotals[category] ?: 0.0,
                                cut = cut,
                                languageCode = languageCode
                            )
                        }
                    }
                }
            )
        }

        item {
            ExpenseSectionCard(
                title = tr("spending_limits", languageCode),
                subtitle = "Set monthly limits for your categories.",
                content = {
                    expenseCategories.forEach { category ->
                        val spent = categoryTotals[category] ?: 0.0
                        val limit = spendingLimits[category]
                        ExpenseLimitRow(
                            category = category,
                            spent = spent,
                            limit = limit,
                            languageCode = languageCode,
                            onSetLimit = { limitCategory = category }
                        )
                    }
                }
            )
        }

        item {
            ExpenseSectionCard(
                title = tr("smart_alerts", languageCode),
                subtitle = "Spending alerts based on your actual expenses.",
                content = {
                    val alerts = expenseCategories.mapNotNull { category ->
                        val spent = categoryTotals[category] ?: 0.0
                        val limit = spendingLimits[category]
                        when {
                            limit != null && spent >= limit ->
                                "${tr(expenseCategoryKey(category), languageCode)}: ${tr("high_spending", languageCode)} — ${formatPayWiseMoney(spent)} / ${formatPayWiseMoney(limit)}"
                            limit != null && spent >= limit * 0.8 ->
                                "${tr(expenseCategoryKey(category), languageCode)}: ${tr("approaching_limit", languageCode)} — ${formatPayWiseMoney(spent)} / ${formatPayWiseMoney(limit)}"
                            else -> null
                        }
                    }
                    if (alerts.isEmpty()) {
                        Text("No spending alerts right now.", color = SuccessGreen)
                    } else {
                        alerts.forEach { alert ->
                            Text(
                                text = alert,
                                color = ErrorRed,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }
                }
            )
        }

        item {
            ExpenseSectionCard(
                title = tr("financial_health_tips", languageCode),
                subtitle = "Simple actions based on your spending pattern.",
                content = {
                    FinancialHealthTip(
                        title = tr("build_emergency_fund", languageCode),
                        text = "Try setting aside part of your available money every month."
                    )
                    FinancialHealthTip(
                        title = tr("monitor_recurring", languageCode),
                        text = "Review subscriptions and recurring expenses regularly."
                    )
                    FinancialHealthTip(
                        title = tr("avoid_new_emi", languageCode),
                        text = "Check your existing EMI commitment before adding another EMI."
                    )
                    FinancialHealthTip(
                        title = tr("set_savings_goal", languageCode),
                        text = "Use your potential monthly savings as a starting goal."
                    )
                }
            )
        }

        item {
            ExpenseSectionCard(
                title = tr("summary", languageCode),
                subtitle = tr("track_daily_spending", languageCode),
                content = {
                    SummaryMoneyRow(tr("monthly_salary", languageCode), salary)
                    SummaryMoneyRow(tr("total_emi", languageCode), totalEmi)
                    SummaryMoneyRow(tr("total_spent", languageCode), totalSpent)
                    SummaryMoneyRow(tr("available_money", languageCode), availableMoney)
                    SummaryMoneyRow(tr("potential_monthly_savings", languageCode), potentialSavings)
                }
            )
        }

        item {
            ExpenseSectionCard(
                title = tr("recent_expenses", languageCode),
                subtitle = "Your latest entries.",
                content = {
                    if (expenses.isEmpty()) {
                        Text(tr("no_expenses", languageCode), color = GreyText)
                    } else {
                        expenses.takeLast(8).asReversed().forEach { expense ->
                            ExpenseRecentRow(expense, languageCode)
                        }
                    }
                }
            )
        }
    }

    if (showAddExpense) {
        AddExpenseDialog(
            languageCode = languageCode,
            nextId = (expenses.maxOfOrNull { it.id } ?: 0) + 1,
            onSave = {
                onAddExpense(it)
                showAddExpense = false
            },
            onDismiss = { showAddExpense = false }
        )
    }

    if (limitCategory != null) {
        SetSpendingLimitDialog(
            category = limitCategory!!,
            languageCode = languageCode,
            currentLimit = spendingLimits[limitCategory!!] ?: 0.0,
            onSave = { limit ->
                onSetLimit(limitCategory!!, limit)
                limitCategory = null
            },
            onDismiss = { limitCategory = null }
        )
    }
}

@Composable
fun ExpenseOverviewCard(
    totalSpent: Double,
    categoryTotals: Map<String, Double>,
    languageCode: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = LightBlue)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(
                text = tr("monthly_overview", languageCode),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = DeepBlue
            )
            Spacer(Modifier.height(8.dp))
            Text(tr("total_spent", languageCode), color = GreyText)
            Text(
                text = formatPayWiseMoney(totalSpent),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = DeepBlue
            )
            Spacer(Modifier.height(12.dp))
            categoryTotals.filter { it.value > 0 }.forEach { (category, amount) ->
                val categoryColor = expenseCategoryColor(category)
                Column(modifier = Modifier.padding(vertical = 5.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .background(categoryColor, CircleShape)
                        )
                        Spacer(Modifier.size(8.dp))
                        Text(
                            text = tr(expenseCategoryKey(category), languageCode),
                            modifier = Modifier.weight(1f),
                            color = DarkText
                        )
                        Text(
                            text = formatPayWiseMoney(amount),
                            fontWeight = FontWeight.Bold,
                            color = categoryColor
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ExpenseCategoryCards(
    categoryTotals: Map<String, Double>,
    languageCode: String
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            text = tr("category_breakdown", languageCode),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = DeepBlue
        )
        expenseCategories.chunked(2).forEach { rowCategories ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                rowCategories.forEach { category ->
                    ExpenseCategoryCard(
                        category = category,
                        amount = categoryTotals[category] ?: 0.0,
                        languageCode = languageCode,
                        modifier = Modifier.weight(1f)
                    )
                }
                if (rowCategories.size == 1) {
                    Spacer(Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun ExpenseCategoryCard(
    category: String,
    amount: Double,
    languageCode: String,
    modifier: Modifier = Modifier
) {
    val categoryColor = expenseCategoryColor(category)
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = categoryColor.copy(alpha = 0.12f))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(categoryColor, CircleShape)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = tr(expenseCategoryKey(category), languageCode),
                fontWeight = FontWeight.Bold,
                color = DarkText
            )
            Spacer(Modifier.height(3.dp))
            Text(
                text = formatPayWiseMoney(amount),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = categoryColor
            )
        }
    }
}

fun expenseCategoryColor(category: String): Color {
    return when (category) {
        "Food" -> Orange
        "Travel" -> PrimaryBlue
        "Shopping" -> Yellow
        "Bills" -> DeepBlue
        "Entertainment" -> ErrorRed
        else -> GreyText
    }
}

@Composable
fun ExpenseSectionCard(
    title: String,
    subtitle: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = BackgroundWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = DeepBlue
            )
            Spacer(Modifier.height(4.dp))
            Text(text = subtitle, color = GreyText, fontSize = 13.sp)
            Spacer(Modifier.height(12.dp))
            content()
        }
    }
}

@Composable
fun ExpenseSuggestionRow(
    category: String,
    spent: Double,
    cut: Double,
    languageCode: String
) {
    val categoryColor = expenseCategoryColor(category)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = categoryColor.copy(alpha = 0.10f)
        )
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(categoryColor, CircleShape)
            )
            Spacer(Modifier.size(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = tr(expenseCategoryKey(category), languageCode),
                    fontWeight = FontWeight.Bold,
                    color = DeepBlue
                )
                Text(
                    text = "${tr("spent", languageCode)}: ${formatPayWiseMoney(spent)}",
                    color = DarkText,
                    fontSize = 13.sp
                )
                Text(
                    text = "${tr("cut", languageCode)} ${formatPayWiseMoney(cut)} → ${tr("save_more", languageCode)} ${formatPayWiseMoney(cut)}",
                    color = SuccessGreen,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }
        }
    }
}

@Composable
fun ExpenseLimitRow(
    category: String,
    spent: Double,
    limit: Double?,
    languageCode: String,
    onSetLimit: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = tr(expenseCategoryKey(category), languageCode),
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Bold,
                color = DarkText
            )
            TextButton(onClick = onSetLimit) {
                Text(
                    text = if (limit == null) tr("set_limit", languageCode)
                    else formatPayWiseMoney(limit),
                    color = PrimaryBlue
                )
            }
        }
        Text(
            text = "${tr("spent", languageCode)}: ${formatPayWiseMoney(spent)}",
            color = GreyText,
            fontSize = 13.sp
        )
        if (limit != null && limit > 0) {
            val progress = (spent / limit).coerceIn(0.0, 1.0).toFloat()
            androidx.compose.material3.LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                color = if (spent >= limit) ErrorRed else PrimaryBlue
            )
        }
    }
}

@Composable
fun FinancialHealthTip(title: String, text: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = LightBlue)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(title, fontWeight = FontWeight.Bold, color = DeepBlue)
            Spacer(Modifier.height(3.dp))
            Text(text, color = DarkText, fontSize = 13.sp)
        }
    }
}

@Composable
fun SummaryMoneyRow(title: String, amount: Double) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, modifier = Modifier.weight(1f), color = DarkText)
        Text(
            text = formatPayWiseMoney(amount),
            fontWeight = FontWeight.Bold,
            color = if (amount < 0) ErrorRed else DeepBlue
        )
    }
}

@Composable
fun ExpenseRecentRow(expense: Expense, languageCode: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = tr(expenseCategoryKey(expense.category), languageCode),
                fontWeight = FontWeight.Bold,
                color = DarkText
            )
            Text(
                text = if (expense.date.isBlank()) "Date not set" else expense.date,
                color = GreyText,
                fontSize = 12.sp
            )
            if (expense.note.isNotBlank()) {
                Text(expense.note, color = GreyText, fontSize = 12.sp)
            }
        }
        Text(
            text = formatPayWiseMoney(expense.amount),
            fontWeight = FontWeight.Bold,
            color = PrimaryBlue
        )
    }
}

@Composable
fun AddExpenseDialog(
    languageCode: String,
    nextId: Int,
    onSave: (Expense) -> Unit,
    onDismiss: () -> Unit
) {
    val currency = LocalPayWiseCurrency.current
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Food") }
    var date by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = tr("add_expense", languageCode),
                fontWeight = FontWeight.Bold,
                color = DeepBlue
            )
        },
        text = {
            Column {
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("${tr("amount", languageCode)} (${currency.symbol})") },
                    singleLine = true
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "${tr("category", languageCode)}: ${tr(expenseCategoryKey(category), languageCode)}",
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )
                LazyColumn(modifier = Modifier.height(105.dp)) {
                    items(expenseCategories) { item ->
                        TextButton(
                            onClick = { category = item },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = tr(expenseCategoryKey(item), languageCode),
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Start,
                                color = if (category == item) PrimaryBlue else DarkText,
                                fontWeight = if (category == item) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
                OutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(tr("date", languageCode)) },
                    placeholder = { Text("DD/MM/YYYY") },
                    singleLine = true
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(tr("note_optional", languageCode)) },
                    singleLine = true
                )
                if (error.isNotBlank()) {
                    Spacer(Modifier.height(6.dp))
                    Text(error, color = ErrorRed)
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val value = amount.toDoubleOrNull()
                    if (value == null || value <= 0.0) {
                        error = "Enter a valid amount."
                    } else {
                        onSave(
                            Expense(
                                id = nextId,
                                // Expenses are stored internally in INR; the user enters the selected currency.
                                amount = value / currency.rateFromINR,
                                category = category,
                                date = date,
                                note = note
                            )
                        )
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
            ) {
                Text(tr("save_expense", languageCode), color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(tr("cancel", languageCode), color = PrimaryBlue)
            }
        }
    )
}

@Composable
fun SetSpendingLimitDialog(
    category: String,
    languageCode: String,
    currentLimit: Double,
    onSave: (Double) -> Unit,
    onDismiss: () -> Unit
) {
    val currency = LocalPayWiseCurrency.current
    var limitText by remember { mutableStateOf(if (currentLimit > 0) "%.0f".format(currentLimit * currency.rateFromINR) else "") }
    var error by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = tr("set_spending_limit", languageCode),
                fontWeight = FontWeight.Bold,
                color = DeepBlue
            )
        },
        text = {
            Column {
                Text(
                    text = tr(expenseCategoryKey(category), languageCode),
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = limitText,
                    onValueChange = { limitText = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("${tr("limit_amount", languageCode)} (${currency.symbol})") },
                    singleLine = true
                )
                if (error.isNotBlank()) {
                    Spacer(Modifier.height(6.dp))
                    Text(error, color = ErrorRed)
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val value = limitText.toDoubleOrNull()
                    if (value == null || value <= 0.0) {
                        error = "Enter a valid limit."
                    } else {
                        // Spending limits are stored internally in INR.
                        onSave(value / currency.rateFromINR)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
            ) {
                Text(tr("set_limit", languageCode), color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(tr("cancel", languageCode), color = PrimaryBlue)
            }
        }
    )
}

// ============================================================
// FINANCIAL SUMMARY
// ============================================================

@Composable
fun FinancialSummaryScreen(
    response: EMIResponseData?,
    languageCode: String
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        item {

            Text(
                text = tr(
                    "financial_summary",
                    languageCode
                ),
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = DeepBlue
            )

            Spacer(
                modifier = Modifier.height(15.dp)
            )
        }

        if (response == null) {

            item {

                Text(
                    text = tr(
                        "no_financial_data",
                        languageCode
                    ),
                    color = GreyText
                )
            }

        } else {

            item {

                FinancialCard(
                    title = tr(
                        "monthly_salary",
                        languageCode
                    ),
                    value =
                        formatPayWiseMoney(response.salary),
                    backgroundColor = LightBlue
                )
            }

            item {

                FinancialCard(
                    title = tr(
                        "total_emi",
                        languageCode
                    ),
                    value =
                        formatPayWiseMoney(response.totalEmi),
                    backgroundColor =
                        Color(0xFFFFF3E0)
                )
            }

            item {

                FinancialCard(
                    title = tr(
                        "remaining_salary",
                        languageCode
                    ),
                    value =
                        formatPayWiseMoney(response.remainingSalary),
                    backgroundColor =
                        Color(0xFFE8F5E9)
                )
            }

            item {

                FinancialCard(
                    title = tr(
                        "emi_ratio",
                        languageCode
                    ),
                    value =
                        "%.2f%%".format(
                            response.emiRatio
                        ),
                    backgroundColor =
                        Color(0xFFFFF8E1)
                )
            }

            item {

                StatusCard(
                    status = response.status,
                    languageCode = languageCode
                )
            }
        }
    }
}

// ============================================================
// PROFILE
// ============================================================

@Composable
fun ProfileScreen(
    userId: Int,
    userName: String,
    userEmail: String,
    userMobile: String,
    emiResponse: EMIResponseData?,
    languageCode: String,
    onProfileUpdated: (String, String, String) -> Unit
) {

    val context = LocalContext.current

    // --------------------------------------------------------
    // Saved profile photo
    // --------------------------------------------------------

    val preferences = remember {
        context.getSharedPreferences(
            "paywise_profile",
            Context.MODE_PRIVATE
        )
    }

    var profilePhotoUri by rememberSaveable {
        mutableStateOf(
            preferences.getString(
                "profile_photo_$userId",
                ""
            ) ?: ""
        )
    }

    // --------------------------------------------------------
    // Profile information
    // --------------------------------------------------------

    var currentName by rememberSaveable(userId) {
        mutableStateOf(userName)
    }

    var currentEmail by rememberSaveable(userId) {
        mutableStateOf(userEmail)
    }

    var currentMobile by rememberSaveable(userId) {
        mutableStateOf(userMobile)
    }

    // --------------------------------------------------------
    // Dialog states
    // --------------------------------------------------------

    var showEditProfile by remember {
        mutableStateOf(false)
    }

    var showChangePassword by remember {
        mutableStateOf(false)
    }

    // --------------------------------------------------------
    // Save message
    // --------------------------------------------------------

    var showSavedMessage by remember {
        mutableStateOf(false)
    }

    // --------------------------------------------------------
    // Photo picker
    // --------------------------------------------------------

    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->

        if (uri != null) {

            try {

                context.contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )

            } catch (_: Exception) {
                // Some providers may not support persistable permission.
            }

            profilePhotoUri = uri.toString()

            // Save photo immediately so it remains after
            // the app is closed/reopened.
            preferences.edit()
                .putString(
                    "profile_photo_$userId",
                    profilePhotoUri
                )
                .apply()

            showSavedMessage = true
        }
    }

    // --------------------------------------------------------
    // Main Profile UI
    // --------------------------------------------------------

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundWhite)
            .padding(16.dp),

        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        // ====================================================
        // HEADER
        // ====================================================

        item {

            Text(
                text = tr(
                    "my_profile",
                    languageCode
                ),

                fontSize = 26.sp,

                fontWeight = FontWeight.Bold,

                color = DeepBlue
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = tr(
                    "profile_information",
                    languageCode
                ),

                fontSize = 15.sp,

                color = GreyText
            )
        }

        // ====================================================
        // PROFILE PHOTO
        // ====================================================

        item {

            ProfileSectionCard(
                title = tr(
                    "profile_photo",
                    languageCode
                )
            ) {

                Box(
                    modifier = Modifier.fillMaxWidth(),

                    contentAlignment = Alignment.Center
                ) {

                    if (profilePhotoUri.isNotBlank()) {

                        val bitmap = remember(profilePhotoUri) {

                            try {

                                context.contentResolver
                                    .openInputStream(
                                        Uri.parse(profilePhotoUri)
                                    )
                                    ?.use {
                                        BitmapFactory
                                            .decodeStream(it)
                                    }

                            } catch (_: Exception) {

                                null
                            }
                        }

                        if (bitmap != null) {

                            Image(
                                bitmap = bitmap.asImageBitmap(),

                                contentDescription = tr(
                                    "profile_photo",
                                    languageCode
                                ),

                                modifier = Modifier
                                    .size(110.dp)
                                    .clip(CircleShape)
                                    .border(
                                        2.dp,
                                        PrimaryBlue,
                                        CircleShape
                                    ),

                                contentScale = ContentScale.Crop
                            )

                        } else {

                            ProfileInitial(currentName)
                        }

                    } else {

                        ProfileInitial(currentName)
                    }
                }

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                TextButton(
                    onClick = {

                        photoPicker.launch(
                            arrayOf("image/*")
                        )
                    },

                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text(
                        text = tr(
                            "select_photo",
                            languageCode
                        ),

                        color = PrimaryBlue
                    )
                }
            }
        }

        // ====================================================
        // PERSONAL INFORMATION
        // ====================================================

        item {

            ProfileSectionCard(
                title = tr(
                    "personal_information",
                    languageCode
                )
            ) {

                ProfileDataRow(
                    tr(
                        "full_name",
                        languageCode
                    ),

                    if (currentName.isNotBlank())
                        currentName
                    else
                        tr(
                            "not_available",
                            languageCode
                        )
                )

                ProfileDataRow(
                    tr(
                        "email",
                        languageCode
                    ),

                    if (currentEmail.isNotBlank())
                        currentEmail
                    else
                        tr(
                            "not_available",
                            languageCode
                        )
                )

                ProfileDataRow(
                    tr(
                        "mobile",
                        languageCode
                    ),

                    if (currentMobile.isNotBlank())
                        currentMobile
                    else
                        tr(
                            "not_available",
                            languageCode
                        )
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Button(
                    onClick = {
                        showEditProfile = true
                    },

                    modifier = Modifier.fillMaxWidth(),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryBlue
                    )
                ) {

                    Text(
                        text = tr(
                            "edit_profile",
                            languageCode
                        ),

                        color = Color.White
                    )
                }
            }
        }

        // ====================================================
        // FINANCIAL PROFILE
        // ====================================================

        item {

            ProfileSectionCard(
                title = tr(
                    "financial_profile",
                    languageCode
                )
            ) {

                ProfileDataRow(
                    tr(
                        "monthly_salary",
                        languageCode
                    ),

                    emiResponse?.let {
                        formatPayWiseMoney(
                            it.salary
                        )
                    }
                        ?: tr(
                            "not_available",
                            languageCode
                        )
                )

                ProfileDataRow(
                    tr(
                        "total_active_emis",
                        languageCode
                    ),

                    emiResponse
                        ?.emiData
                        ?.size
                        ?.toString()
                        ?: tr(
                            "not_available",
                            languageCode
                        )
                )

                ProfileDataRow(
                    tr(
                        "total_monthly_emi",
                        languageCode
                    ),

                    emiResponse?.let {
                        formatPayWiseMoney(
                            it.totalEmi
                        )
                    }
                        ?: tr(
                            "not_available",
                            languageCode
                        )
                )
            }
        }

        // ====================================================
        // ACCOUNT INFORMATION
        // ====================================================

        item {

            ProfileSectionCard(
                title = tr(
                    "account_information",
                    languageCode
                )
            ) {

                ProfileDataRow(
                    tr(
                        "account_creation_date",
                        languageCode
                    ),

                    tr(
                        "not_available",
                        languageCode
                    )
                )

                ProfileDataRow(
                    tr(
                        "user_id",
                        languageCode
                    ),

                    userId.toString()
                )
            }
        }

        // ====================================================
        // SECURITY
        // ====================================================

        item {

            ProfileSectionCard(
                title = tr(
                    "security",
                    languageCode
                )
            ) {

                Button(
                    onClick = {
                        showChangePassword = true
                    },

                    modifier = Modifier.fillMaxWidth(),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Orange
                    )
                ) {

                    Text(
                        text = tr(
                            "change_password",
                            languageCode
                        ),

                        color = Color.White
                    )
                }
            }
        }
    }

    // ========================================================
    // EDIT PROFILE DIALOG
    // ========================================================

    if (showEditProfile) {

        EditProfileDialog(
            userId = userId,

            userName = currentName,

            userEmail = currentEmail,

            userMobile = currentMobile,

            languageCode = languageCode,

            onDismiss = {
                showEditProfile = false
            },

            onSaved = { updatedName, updatedEmail, updatedMobile ->

                currentName = updatedName
                currentEmail = updatedEmail
                currentMobile = updatedMobile

                onProfileUpdated(
                    updatedName,
                    updatedEmail,
                    updatedMobile
                )

                showEditProfile = false

                showSavedMessage = true
            }
        )
    }

    // ========================================================
    // CHANGE PASSWORD
    // ========================================================

    if (showChangePassword) {

        ChangePasswordDialog(
            languageCode = languageCode,

            onDismiss = {
                showChangePassword = false
            }
        )
    }

    // ========================================================
    // SAVE MESSAGE
    // ========================================================

    if (showSavedMessage) {

        LaunchedEffect(Unit) {

            kotlinx.coroutines.delay(1800)

            showSavedMessage = false
        }

        AlertDialog(
            onDismissRequest = {
                showSavedMessage = false
            },

            title = {
                Text(
                    text = tr(
                        "success",
                        languageCode
                    )
                )
            },

            text = {
                Text(
                    text = tr(
                        "Profile Updated Successfully",
                        languageCode
                    )
                )
            },

            confirmButton = {

                TextButton(
                    onClick = {
                        showSavedMessage = false
                    }
                ) {

                    Text(
                        text = "OK",
                        color = PrimaryBlue
                    )
                }
            }
        )
    }
}

@Composable
fun ProfileInitial(userName: String) {
    Box(
        modifier = Modifier
            .size(110.dp)
            .clip(CircleShape)
            .background(LightBlue)
            .border(2.dp, PrimaryBlue, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = userName.trim().firstOrNull()?.uppercase() ?: "P",
            fontSize = 42.sp,
            fontWeight = FontWeight.Bold,
            color = DeepBlue
        )
    }
}

@Composable
fun ProfileSectionCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = LightBlue
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            content = content
        )
    }
}

@Composable
fun ProfileDataRow(
    label: String,
    value: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp)
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            color = GreyText
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = value,
            fontSize = 17.sp,
            fontWeight = FontWeight.Medium,
            color = DarkText
        )
    }
}

@Composable
fun EditProfileDialog(
    userId: Int,
    userName: String,
    userEmail: String,
    userMobile: String,
    languageCode: String,
    onDismiss: () -> Unit,
    onSaved: (String, String, String) -> Unit
) {

    var name by rememberSaveable {
        mutableStateOf(userName)
    }

    var email by rememberSaveable {
        mutableStateOf(userEmail)
    }

    var mobile by rememberSaveable {
        mutableStateOf(userMobile)
    }

    var isSaving by remember {
        mutableStateOf(false)
    }

    var errorMessage by remember {
        mutableStateOf("")
    }

    AlertDialog(

        onDismissRequest = {
            if (!isSaving) {
                onDismiss()
            }
        },

        title = {

            Text(
                text = tr(
                    "edit_profile",
                    languageCode
                )
            )
        },

        text = {

            Column {

                OutlinedTextField(
                    value = name,

                    onValueChange = {
                        name = it
                    },

                    modifier = Modifier.fillMaxWidth(),

                    label = {
                        Text(
                            tr(
                                "full_name",
                                languageCode
                            )
                        )
                    },

                    singleLine = true,

                    enabled = !isSaving
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                OutlinedTextField(
                    value = email,

                    onValueChange = {
                        email = it
                    },

                    modifier = Modifier.fillMaxWidth(),

                    label = {
                        Text(
                            tr(
                                "email",
                                languageCode
                            )
                        )
                    },

                    singleLine = true,

                    enabled = !isSaving
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                OutlinedTextField(
                    value = mobile,

                    onValueChange = {
                        mobile = it
                    },

                    modifier = Modifier.fillMaxWidth(),

                    label = {
                        Text(
                            tr(
                                "mobile",
                                languageCode
                            )
                        )
                    },

                    singleLine = true,

                    enabled = !isSaving
                )

                if (errorMessage.isNotBlank()) {

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text = errorMessage,

                        color = ErrorRed,

                        fontSize = 13.sp
                    )
                }
            }
        },

        confirmButton = {

            TextButton(

                onClick = {

                    val cleanName = name.trim()
                    val cleanEmail = email.trim().lowercase()
                    val cleanMobile = mobile.trim()

                    if (
                        cleanName.isBlank() ||
                        cleanEmail.isBlank() ||
                        cleanMobile.isBlank()
                    ) {

                        errorMessage = tr(
                            "please_fill_all_fields",
                            languageCode
                        )

                        return@TextButton
                    }

                    isSaving = true

                    updateProfileOnServer(
                        userId = userId,

                        name = cleanName,

                        email = cleanEmail,

                        mobile = cleanMobile,

                        onSuccess = {

                            isSaving = false

                            onSaved(
                                cleanName,
                                cleanEmail,
                                cleanMobile
                            )
                        },

                        onError = { message ->

                            isSaving = false

                            errorMessage = message
                        }
                    )
                },

                enabled = !isSaving
            ) {

                Text(
                    text = if (isSaving)
                        "Saving..."
                    else
                        tr(
                            "save_changes",
                            languageCode
                        ),

                    color = PrimaryBlue
                )
            }
        },

        dismissButton = {

            TextButton(

                onClick = onDismiss,

                enabled = !isSaving
            ) {

                Text(
                    text = tr(
                        "cancel",
                        languageCode
                    ),

                    color = GreyText
                )
            }
        }
    )
}

@Composable
fun ChangePasswordDialog(
    languageCode: String,
    onDismiss: () -> Unit
) {
    var currentPassword by rememberSaveable { mutableStateOf("") }
    var newPassword by rememberSaveable { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(tr("change_password", languageCode))
        },
        text = {
            Column {
                OutlinedTextField(
                    value = currentPassword,
                    onValueChange = { currentPassword = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(tr("current_password", languageCode)) },
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(tr("new_password", languageCode)) },
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(tr("save_changes", languageCode), color = PrimaryBlue)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(tr("cancel", languageCode), color = GreyText)
            }
        }
    )
}

// ============================================================
// SETTINGS
// ============================================================

@Composable
fun SettingsScreen(
    selectedLanguageCode: String,
    selectedCurrencyCode: String,
    selectedReminderDays: Int,
    onLanguageChanged: (String) -> Unit,
    onCurrencyChanged: (String) -> Unit,
    onReminderDaysChanged: (Int) -> Unit
) {

    var emiDueReminders by rememberSaveable {
        mutableStateOf(true)
    }

    var paymentReminders by rememberSaveable {
        mutableStateOf(true)
    }

    var showFinancialStatus by rememberSaveable {
        mutableStateOf(true)
    }


    var selectedCountryCode by rememberSaveable {
        mutableStateOf("IN")
    }

    var showCurrencyDialog by remember {
        mutableStateOf(false)
    }

    var showCountryDialog by remember {
        mutableStateOf(false)
    }

    var showLanguageDialog by remember {
        mutableStateOf(false)
    }

    val countries = remember {

        Locale.getISOCountries()
            .map { code ->

                val locale = Locale(
                    "",
                    code
                )

                CountryOption(
                    code = code,
                    name = locale.getDisplayCountry(
                        Locale.ENGLISH
                    )
                )
            }

            .filter {
                it.name.isNotBlank()
            }

            .distinctBy {
                it.code
            }

            .sortedBy {
                it.name.lowercase()
            }
    }

    val languages = remember {

        listOf(
            LanguageOption("en", "English"),
            LanguageOption("hi", "Hindi"),
            LanguageOption("mr", "Marathi"),
            LanguageOption("bn", "Bengali"),
            LanguageOption("gu", "Gujarati"),
            LanguageOption("ta", "Tamil"),
            LanguageOption("te", "Telugu"),
            LanguageOption("es", "Spanish"),
            LanguageOption("fr", "French"),
            LanguageOption("de", "German"),
            LanguageOption("ja", "Japanese"),
            LanguageOption("ko", "Korean"),
            LanguageOption("zh", "Chinese"),
            LanguageOption("ar", "Arabic")
        )
    }

    val currencies = remember {

        Currency.getAvailableCurrencies()
            .map { currency ->

                CurrencyOption(
                    code = currency.currencyCode,
                    name = currency.getDisplayName(
                        Locale.ENGLISH
                    ),
                    symbol = currency.getSymbol(
                        Locale.ENGLISH
                    )
                )
            }

            .distinctBy {
                it.code
            }

            .sortedBy {
                it.code
            }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        item {

            Text(
                text = tr(
                    "settings",
                    selectedLanguageCode
                ),
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = DeepBlue
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            SettingsSectionTitle(
                text = tr(
                    "notifications",
                    selectedLanguageCode
                )
            )

            SettingsSwitchRow(
                title = tr(
                    "emi_due_reminders",
                    selectedLanguageCode
                ),
                checked = emiDueReminders,
                onCheckedChange = {
                    emiDueReminders = it
                }
            )

            SettingsSwitchRow(
                title = tr(
                    "payment_reminders",
                    selectedLanguageCode
                ),
                checked = paymentReminders,
                onCheckedChange = {
                    paymentReminders = it
                }
            )

            SettingsSelectionRow(
                title = tr(
                    "reminder_days",
                    selectedLanguageCode
                ),

                value =
                    "$selectedReminderDays " +
                            tr(
                                "days",
                                selectedLanguageCode
                            ),

                onClick = {

                    onReminderDaysChanged(
                        when (selectedReminderDays) {
                            1 -> 3
                            3 -> 5
                            5 -> 7
                            else -> 1
                        }
                    )
                }
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            SettingsSectionTitle(
                text = tr(
                    "financial_preferences",
                    selectedLanguageCode
                )
            )

            SettingsSelectionRow(
                title = tr(
                    "currency",
                    selectedLanguageCode
                ),
                value = selectedCurrencyCode,
                onClick = {
                    showCurrencyDialog = true
                }
            )

            SettingsSelectionRow(
                title = tr(
                    "primary_currency",
                    selectedLanguageCode
                ),
                value = selectedCurrencyCode,
                onClick = {
                    showCurrencyDialog = true
                }
            )

            SettingsSwitchRow(
                title = tr(
                    "show_financial_status",
                    selectedLanguageCode
                ),
                checked = showFinancialStatus,
                onCheckedChange = {
                    showFinancialStatus = it
                }
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            SettingsSectionTitle(
                text = tr(
                    "language",
                    selectedLanguageCode
                )
            )

            val selectedLanguage =
                languages.firstOrNull {
                    it.code == selectedLanguageCode
                }

            SettingsSelectionRow(
                title = tr(
                    "app_language",
                    selectedLanguageCode
                ),

                value =
                    selectedLanguage?.name
                        ?: "English",

                onClick = {
                    showLanguageDialog = true
                }
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            SettingsSectionTitle(
                text = tr(
                    "region",
                    selectedLanguageCode
                )
            )

            val selectedCountry =
                countries.firstOrNull {
                    it.code == selectedCountryCode
                }

            SettingsSelectionRow(
                title = tr(
                    "country_region",
                    selectedLanguageCode
                ),

                value =
                    selectedCountry?.name
                        ?: "India",

                onClick = {
                    showCountryDialog = true
                }
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            SettingsSectionTitle(
                text = tr(
                    "about_paywise",
                    selectedLanguageCode
                )
            )

            Card(
                modifier = Modifier.fillMaxWidth(),

                colors = CardDefaults.cardColors(
                    containerColor = LightBlue
                )
            ) {

                Column(
                    modifier = Modifier.padding(18.dp)
                ) {

                    Text(
                        text = tr(
                            "paywise",
                            selectedLanguageCode
                        ),
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold,
                        color = DeepBlue
                    )

                    Spacer(
                        modifier = Modifier.height(5.dp)
                    )

                    Text(
                        text = tr(
                            "version",
                            selectedLanguageCode
                        ),
                        color = DarkText
                    )

                    Text(
                        text = tr(
                            "tagline",
                            selectedLanguageCode
                        ),
                        color = GreyText
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(25.dp)
            )
        }
    }

    // ========================================================
    // CURRENCY DIALOG
    // ========================================================

    if (showCurrencyDialog) {

        SearchSelectionDialog(
            title = tr(
                "select_currency",
                selectedLanguageCode
            ),

            searchHint = tr(
                "search_currency",
                selectedLanguageCode
            ),

            items = currencies,

            itemText = {
                "${it.code}  ${it.symbol}  ${it.name}"
            },

            noResultsText = tr(
                "no_results",
                selectedLanguageCode
            ),

            closeText = tr(
                "close",
                selectedLanguageCode
            ),

            onItemSelected = {

                onCurrencyChanged(it.code)
                showCurrencyDialog = false
            },

            onDismiss = {
                showCurrencyDialog = false
            }
        )
    }

    // ========================================================
    // COUNTRY DIALOG
    // ========================================================

    if (showCountryDialog) {

        SearchSelectionDialog(
            title = tr(
                "select_country",
                selectedLanguageCode
            ),

            searchHint = tr(
                "search_country",
                selectedLanguageCode
            ),

            items = countries,

            itemText = {
                it.name
            },

            noResultsText = tr(
                "no_results",
                selectedLanguageCode
            ),

            closeText = tr(
                "close",
                selectedLanguageCode
            ),

            onItemSelected = {

                selectedCountryCode = it.code
                showCountryDialog = false
            },

            onDismiss = {
                showCountryDialog = false
            }
        )
    }

    // ========================================================
    // LANGUAGE DIALOG
    // ========================================================

    if (showLanguageDialog) {

        SearchSelectionDialog(
            title = tr(
                "select_language",
                selectedLanguageCode
            ),

            searchHint = tr(
                "search_language",
                selectedLanguageCode
            ),

            items = languages,

            itemText = {
                it.name
            },

            noResultsText = tr(
                "no_results",
                selectedLanguageCode
            ),

            closeText = tr(
                "close",
                selectedLanguageCode
            ),

            onItemSelected = {

                onLanguageChanged(it.code)

                showLanguageDialog = false
            },

            onDismiss = {
                showLanguageDialog = false
            }
        )
    }
}

// ============================================================
// SETTINGS COMPONENTS
// ============================================================

@Composable
fun SettingsSectionTitle(
    text: String
) {

    Text(
        text = text,
        fontSize = 19.sp,
        fontWeight = FontWeight.Bold,
        color = DeepBlue,
        modifier = Modifier.padding(
            vertical = 8.dp
        )
    )
}

@Composable
fun SettingsSwitchRow(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 6.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = title,
            modifier = Modifier.weight(1f),
            color = DarkText,
            fontSize = 16.sp
        )

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,

            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = PrimaryBlue
            )
        )
    }
}

@Composable
fun SettingsSelectionRow(
    title: String,
    value: String,
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(
                vertical = 14.dp
            ),

        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = title,
            modifier = Modifier.weight(1f),
            color = DarkText,
            fontSize = 16.sp
        )

        Text(
            text = value,
            color = PrimaryBlue,
            fontWeight = FontWeight.Bold
        )
    }
}

// ============================================================
// SEARCH SELECTION DIALOG
// ============================================================

@Composable
fun <T> SearchSelectionDialog(
    title: String,
    searchHint: String,
    items: List<T>,
    itemText: (T) -> String,
    noResultsText: String,
    closeText: String,
    onItemSelected: (T) -> Unit,
    onDismiss: () -> Unit
) {

    var searchText by remember {
        mutableStateOf("")
    }

    val filteredItems = items.filter { item ->

        itemText(item).contains(
            searchText,
            ignoreCase = true
        )
    }

    AlertDialog(

        onDismissRequest = onDismiss,

        title = {

            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                color = DeepBlue
            )
        },

        text = {

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {

                OutlinedTextField(
                    value = searchText,

                    onValueChange = {
                        searchText = it
                    },

                    modifier = Modifier.fillMaxWidth(),

                    singleLine = true,

                    placeholder = {
                        Text(searchHint)
                    }
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                if (filteredItems.isEmpty()) {

                    Text(
                        text = noResultsText,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),

                        textAlign = TextAlign.Center,

                        color = GreyText
                    )

                } else {

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(350.dp)
                    ) {

                        items(
                            count = filteredItems.size
                        ) { index ->

                            val item =
                                filteredItems[index]

                            TextButton(

                                onClick = {
                                    onItemSelected(item)
                                },

                                modifier =
                                    Modifier.fillMaxWidth()
                            ) {

                                Text(
                                    text = itemText(item),

                                    modifier =
                                        Modifier.fillMaxWidth(),

                                    textAlign =
                                        TextAlign.Start,

                                    color = DarkText
                                )
                            }
                        }
                    }
                }
            }
        },

        confirmButton = {

            TextButton(
                onClick = onDismiss
            ) {

                Text(
                    text = closeText,
                    color = PrimaryBlue
                )
            }
        }
    )
}

// ============================================================
// API - LOGIN
// ============================================================

fun loginUser(
    email: String,
    password: String,
    onSuccess: (Int, String, String, String) -> Unit,
    onError: (String) -> Unit
) {

    val client = OkHttpClient()

    val json = """
        {
            "email": "${escapeJson(email)}",
            "password": "${escapeJson(password)}"
        }
    """.trimIndent()

    val body = json.toRequestBody(
        "application/json".toMediaType()
    )

    val request = Request.Builder()
        .url("$BASE_URL/login")
        .post(body)
        .build()

    client.newCall(request).enqueue(
        object : Callback {

            override fun onFailure(
                call: Call,
                e: IOException
            ) {

                Handler(
                    Looper.getMainLooper()
                ).post {

                    onError(
                        "Unable to connect to server: ${e.message}"
                    )
                }
            }

            override fun onResponse(
                call: Call,
                response: Response
            ) {

                response.use {

                    val responseBody =
                        it.body?.string().orEmpty()

                    if (!it.isSuccessful) {

                        Handler(
                            Looper.getMainLooper()
                        ).post {

                            onError(
                                "Login failed: ${it.code}"
                            )
                        }

                        return
                    }

                    try {

                        val jsonObject =
                            JsonParser
                                .parseString(responseBody)
                                .asJsonObject

                        val apiSuccess =
                            jsonObject.get("status")
                                ?.asString == "success" ||
                                    jsonObject.get("success")
                                        ?.asBoolean == true

                        if (!apiSuccess) {

                            Handler(
                                Looper.getMainLooper()
                            ).post {

                                onError(
                                    jsonObject.get("message")
                                        ?.asString
                                        ?: "Invalid login details."
                                )
                            }

                            return
                        }

                        val id =
                            jsonObject.get("user_id")
                                ?.asInt
                                ?: jsonObject.get("id")
                                    ?.asInt
                                ?: 0

                        val name =
                            jsonObject.get("name")
                                ?.asString
                                ?: ""

                        val userEmail =
                            jsonObject.get("email")
                                ?.asString
                                ?: email

                        val mobile =
                            jsonObject.get("mobile")
                                ?.asString
                                ?: ""

                        Handler(
                            Looper.getMainLooper()
                        ).post {

                            onSuccess(
                                id,
                                name,
                                userEmail,
                                mobile
                            )
                        }

                    } catch (
                        e: Exception
                    ) {

                        Handler(
                            Looper.getMainLooper()
                        ).post {

                            onError(
                                "Invalid server response."
                            )
                        }
                    }
                }
            }
        }
    )
}

// ============================================================
// API - SIGNUP
// ============================================================

fun signupUser(
    name: String,
    email: String,
    mobile: String,
    password: String,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
) {

    val client = OkHttpClient()

    val json = """
        {
            "name": "${escapeJson(name)}",
            "email": "${escapeJson(email)}",
            "mobile": "${escapeJson(mobile)}",
            "password": "${escapeJson(password)}"
        }
    """.trimIndent()

    val body = json.toRequestBody(
        "application/json".toMediaType()
    )

    val request = Request.Builder()
        .url("$BASE_URL/signup")
        .post(body)
        .build()

    client.newCall(request).enqueue(
        object : Callback {

            override fun onFailure(
                call: Call,
                e: IOException
            ) {

                Handler(
                    Looper.getMainLooper()
                ).post {

                    onError(
                        "Unable to connect to server: ${e.message}"
                    )
                }
            }

            override fun onResponse(
                call: Call,
                response: Response
            ) {

                response.use {

                    val responseBody =
                        it.body?.string().orEmpty()

                    if (!it.isSuccessful) {

                        Handler(
                            Looper.getMainLooper()
                        ).post {

                            onError(
                                "Signup failed: ${it.code}"
                            )
                        }

                        return
                    }

                    try {

                        val jsonObject =
                            JsonParser
                                .parseString(responseBody)
                                .asJsonObject

                        val success =
                            jsonObject.get("status")
                                ?.asString == "success" ||
                                    jsonObject.get("success")
                                        ?.asBoolean == true

                        Handler(
                            Looper.getMainLooper()
                        ).post {

                            if (success) {

                                onSuccess()

                            } else {

                                onError(
                                    jsonObject.get("message")
                                        ?.asString
                                        ?: "Signup failed."
                                )
                            }
                        }

                    } catch (
                        e: Exception
                    ) {

                        Handler(
                            Looper.getMainLooper()
                        ).post {

                            onError(
                                "Invalid server response."
                            )
                        }
                    }
                }
            }
        }
    )
}

// ============================================================
// API - GET EMI DATA
// ============================================================

fun getEMIData(
    userId: Int,
    onSuccess: (EMIResponseData) -> Unit,
    onError: (String) -> Unit
) {

    val client = OkHttpClient()

    val request = Request.Builder()
        .url(
            "$BASE_URL/emi-data?user_id=$userId"
        )
        .get()
        .build()

    client.newCall(request).enqueue(
        object : Callback {

            override fun onFailure(
                call: Call,
                e: IOException
            ) {

                Handler(
                    Looper.getMainLooper()
                ).post {

                    onError(
                        "Unable to load EMI data. ${e.message}"
                    )
                }
            }

            override fun onResponse(
                call: Call,
                response: Response
            ) {

                response.use {

                    val responseBody =
                        it.body?.string().orEmpty()

                    if (!it.isSuccessful) {

                        Handler(
                            Looper.getMainLooper()
                        ).post {

                            onError(
                                "Server error: ${it.code}"
                            )
                        }

                        return
                    }

                    try {

                        val jsonObject =
                            JsonParser
                                .parseString(responseBody)
                                .asJsonObject

                        val apiSuccess =
                            jsonObject.get("status")
                                ?.asString == "success" ||
                                    jsonObject.get("success")
                                        ?.asBoolean == true

                        if (!apiSuccess) {

                            Handler(
                                Looper.getMainLooper()
                            ).post {

                                onError(
                                    jsonObject.get("message")
                                        ?.asString
                                        ?: "Unable to load EMI data."
                                )
                            }

                            return
                        }

                        val emiList =
                            mutableListOf<EMIData>()

                        val emiArray =
                            jsonObject.getAsJsonArray(
                                "emiData"
                            )

                        if (emiArray != null) {

                            emiArray.forEach { element ->

                                val obj =
                                    element.asJsonObject

                                emiList.add(

                                    EMIData(

                                        id =
                                            obj.get("id")
                                                ?.asInt
                                                ?: 0,

                                        lender =
                                            obj.get("lender")
                                                ?.asString
                                                ?: "",

                                        amount =
                                            obj.get("amount")
                                                ?.asDouble
                                                ?: 0.0,

                                        dueDate =
                                            obj.get("dueDate")
                                                ?.asString
                                                ?: "",

                                        frequency =
                                            obj.get("frequency")
                                                ?.asString
                                                ?: "Monthly"
                                    )
                                )
                            }
                        }

                        val result =
                            EMIResponseData(

                                success = true,

                                userId =
                                    jsonObject.get(
                                        "user_id"
                                    )
                                        ?.asInt
                                        ?: userId,

                                name =
                                    jsonObject.get("name")
                                        ?.asString
                                        ?: "",

                                email =
                                    jsonObject.get("email")
                                        ?.asString
                                        ?: "",

                                mobile =
                                    jsonObject.get("mobile")
                                        ?.asString
                                        ?: "",

                                salary =
                                    jsonObject.get("salary")
                                        ?.asDouble
                                        ?: 0.0,

                                emiData = emiList,

                                totalEmi =
                                    jsonObject.get(
                                        "totalEmi"
                                    )
                                        ?.asDouble
                                        ?: 0.0,

                                emiRatio =
                                    jsonObject.get(
                                        "emiRatio"
                                    )
                                        ?.asDouble
                                        ?: 0.0,

                                remainingSalary =
                                    jsonObject.get(
                                        "remainingSalary"
                                    )
                                        ?.asDouble
                                        ?: 0.0,

                                status =
                                    when {

                                        jsonObject.has(
                                            "financialStatus"
                                        ) -> {

                                            jsonObject
                                                .get(
                                                    "financialStatus"
                                                )
                                                .asString
                                                .trim()
                                                .uppercase()
                                        }

                                        jsonObject.has(
                                            "financial_status"
                                        ) -> {

                                            jsonObject
                                                .get(
                                                    "financial_status"
                                                )
                                                .asString
                                                .trim()
                                                .uppercase()
                                        }

                                        jsonObject.has(
                                            "status_text"
                                        ) -> {

                                            jsonObject
                                                .get(
                                                    "status_text"
                                                )
                                                .asString
                                                .trim()
                                                .uppercase()
                                        }

                                        else -> ""
                                    }
                            )

                        Handler(
                            Looper.getMainLooper()
                        ).post {

                            onSuccess(result)
                        }

                    } catch (
                        e: Exception
                    ) {

                        Handler(
                            Looper.getMainLooper()
                        ).post {

                            onError(
                                "Error reading EMI data."
                            )
                        }
                    }
                }
            }
        }
    )
}

// ============================================================
// API - UPDATE SALARY
// ============================================================

fun updateSalary(
    userId: Int,
    salary: Double,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
) {

    val client = OkHttpClient()

    val json = """
        {
            "user_id": $userId,
            "salary": $salary
        }
    """.trimIndent()

    val body = json.toRequestBody(
        "application/json".toMediaType()
    )

    val request = Request.Builder()
        .url("$BASE_URL/update-salary")
        .post(body)
        .build()

    client.newCall(request).enqueue(
        object : Callback {

            override fun onFailure(
                call: Call,
                e: IOException
            ) {

                Handler(
                    Looper.getMainLooper()
                ).post {

                    onError(
                        "Unable to connect: ${e.message}"
                    )
                }
            }

            override fun onResponse(
                call: Call,
                response: Response
            ) {

                response.use {

                    Handler(
                        Looper.getMainLooper()
                    ).post {

                        if (it.isSuccessful) {

                            onSuccess()

                        } else {

                            onError(
                                "Unable to update salary."
                            )
                        }
                    }
                }
            }
        }
    )
}

// ============================================================
// API - ADD EMI
// ============================================================

fun addEMI(
    userId: Int,
    lender: String,
    amount: Double,
    dueDate: String,
    frequency: String,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
) {

    val client = OkHttpClient()

    val json = """
        {
            "user_id": $userId,
            "lender": "${escapeJson(lender)}",
            "amount": $amount,
            "dueDate": "${escapeJson(dueDate)}",
            "frequency": "${escapeJson(frequency)}"
        }
    """.trimIndent()

    val body = json.toRequestBody(
        "application/json".toMediaType()
    )

    val request = Request.Builder()
        .url("$BASE_URL/add-emi")
        .post(body)
        .build()

    client.newCall(request).enqueue(
        object : Callback {

            override fun onFailure(
                call: Call,
                e: IOException
            ) {

                Handler(
                    Looper.getMainLooper()
                ).post {

                    onError(
                        "Unable to connect: ${e.message}"
                    )
                }
            }

            override fun onResponse(
                call: Call,
                response: Response
            ) {

                response.use {

                    Handler(
                        Looper.getMainLooper()
                    ).post {

                        if (it.isSuccessful) {

                            onSuccess()

                        } else {

                            onError(
                                "Unable to add EMI."
                            )
                        }
                    }
                }
            }
        }
    )
}

// ============================================================
// JSON HELPER
// ============================================================

fun escapeJson(
    value: String
): String {

    return value
        .replace("\\", "\\\\")
        .replace("\"", "\\\"")
        .replace("\n", "\\n")
        .replace("\r", "\\r")
}

fun updateProfileOnServer(
    userId: Int,
    name: String,
    email: String,
    mobile: String,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
) {

    val client = okhttp3.OkHttpClient()

    val json = """
        {
            "user_id": $userId,
            "name": ${com.google.gson.Gson().toJson(name)},
            "email": ${com.google.gson.Gson().toJson(email)},
            "mobile": ${com.google.gson.Gson().toJson(mobile)}
        }
    """.trimIndent()

    val requestBody = json.toRequestBody(
        "application/json; charset=utf-8".toMediaType()
    )

    val request = okhttp3.Request.Builder()
        .url("$BASE_URL/update-profile")
        .post(requestBody)
        .build()

    client.newCall(request).enqueue(
        object : okhttp3.Callback {

            override fun onFailure(
                call: okhttp3.Call,
                e: java.io.IOException
            ) {

                android.os.Handler(
                    android.os.Looper.getMainLooper()
                ).post {

                    onError(
                        "Unable to update profile. Please try again."
                    )
                }
            }

            override fun onResponse(
                call: okhttp3.Call,
                response: okhttp3.Response
            ) {

                response.use {

                    val responseBody =
                        it.body?.string() ?: ""

                    android.os.Handler(
                        android.os.Looper.getMainLooper()
                    ).post {

                        if (it.isSuccessful) {

                            onSuccess()

                        } else {

                            try {

                                val errorJson =
                                    com.google.gson.JsonParser
                                        .parseString(responseBody)
                                        .asJsonObject

                                val message =
                                    errorJson
                                        .get("message")
                                        ?.asString
                                        ?: "Unable to update profile."

                                onError(message)

                            } catch (_: Exception) {

                                onError(
                                    "Unable to update profile. Please try again."
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}