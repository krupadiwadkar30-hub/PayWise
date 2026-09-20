package com.example.emishield

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.runtime.CompositionLocalProvider
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

const val BASE_URL = "http://192.168.0.102:5000"

// ============================================================
// LANGUAGE TRANSLATION
// ============================================================

fun tr(
    key: String,
    language: String
): String {
    return when (language) {
        "hi" -> hiText(key)
        "mr" -> mrText(key)
        "bn" -> bnText(key)
        "gu" -> guText(key)
        "ta" -> taText(key)
        "te" -> teText(key)
        "es" -> esText(key)
        "fr" -> frText(key)
        "de" -> deText(key)
        "ja" -> jaText(key)
        "ko" -> koText(key)
        "zh" -> zhText(key)
        "ar" -> arText(key)
        else -> englishText(key)
    }
}
fun hiText(key: String): String {
    return when (key) {
        "paywise" -> "PayWise"
        "tagline" -> "समझदारी से खर्च करें, समझदारी से रहें।"
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
        "manage_finances" -> "अपने वित्त को समझदारी से प्रबंधित करें।"
        "loading_financial_data" -> "वित्तीय डेटा लोड हो रहा है..."
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
        "due_date" -> "देय तिथि"
        "frequency" -> "आवृत्ति"
        "monthly" -> "मासिक"
        "valid_emi" -> "कृपया सही EMI विवरण दर्ज करें।"
        "emi_added" -> "EMI सफलतापूर्वक जोड़ी गई।"
        "no_financial_data" -> "वित्तीय डेटा उपलब्ध नहीं है।"
        "my_profile" -> "मेरी प्रोफ़ाइल"
        "not_available" -> "उपलब्ध नहीं"
        "notifications" -> "सूचनाएं"
        "emi_due_reminders" -> "EMI देय रिमाइंडर"
        "payment_reminders" -> "भुगतान रिमाइंडर"
        "reminder_days" -> "रिमाइंडर के दिन"
        "days" -> "दिन"
        "financial_preferences" -> "वित्तीय प्राथमिकताएं"
        "currency" -> "मुद्रा"
        "primary_currency" -> "मुख्य मुद्रा"
        "show_financial_status" -> "वित्तीय स्थिति दिखाएं"
        "language" -> "भाषा"
        "app_language" -> "ऐप की भाषा"
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
        "user_id" -> "उपयोगकर्ता ID"
        "security" -> "सुरक्षा"
        "change_password" -> "पासवर्ड बदलें"
        "current_password" -> "वर्तमान पासवर्ड"
        "new_password" -> "नया पासवर्ड"
        "save_changes" -> "परिवर्तन सहेजें"
        "cancel" -> "रद्द करें"
        else -> englishText(key)
    }
}

fun mrText(key: String): String {
    return when (key) {
        "paywise" -> "PayWise"
        "tagline" -> "शहाणपणाने खर्च करा, शहाणपणाने जगा."
        "login" -> "लॉगिन"
        "signup" -> "नवीन खाते तयार करा"
        "create_account" -> "खाते तयार करा"
        "back_to_login" -> "लॉगिनकडे परत जा"
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
        "manage_finances" -> "तुमचे आर्थिक व्यवहार शहाणपणाने व्यवस्थापित करा."
        "loading_financial_data" -> "आर्थिक माहिती लोड होत आहे..."
        "monthly_salary" -> "मासिक पगार"
        "total_emi" -> "एकूण EMI"
        "remaining_salary" -> "उर्वरित पगार"
        "emi_ratio" -> "EMI प्रमाण"
        "financial_status" -> "आर्थिक स्थिती"
        "your_emis" -> "तुमच्या EMI"
        "no_emi_records" -> "कोणतेही EMI रेकॉर्ड सापडले नाहीत."
        "add_update_salary" -> "पगार जोडा / अपडेट करा"
        "save_salary" -> "पगार जतन करा"
        "enter_valid_salary" -> "वैध पगार प्रविष्ट करा."
        "salary_updated" -> "पगार यशस्वीरित्या अपडेट झाला."
        "bank_lender" -> "बँक / कर्जदाता"
        "emi_amount" -> "EMI रक्कम"
        "due_date" -> "देय तारीख"
        "frequency" -> "वारंवारता"
        "monthly" -> "मासिक"
        "valid_emi" -> "कृपया योग्य EMI माहिती प्रविष्ट करा."
        "emi_added" -> "EMI यशस्वीरित्या जोडली."
        "no_financial_data" -> "आर्थिक माहिती उपलब्ध नाही."
        "my_profile" -> "माझे प्रोफाइल"
        "not_available" -> "उपलब्ध नाही"
        "notifications" -> "सूचना"
        "emi_due_reminders" -> "EMI देय स्मरणपत्रे"
        "payment_reminders" -> "पेमेंट स्मरणपत्रे"
        "reminder_days" -> "स्मरणपत्राचे दिवस"
        "days" -> "दिवस"
        "financial_preferences" -> "आर्थिक प्राधान्ये"
        "currency" -> "चलन"
        "primary_currency" -> "मुख्य चलन"
        "show_financial_status" -> "आर्थिक स्थिती दाखवा"
        "language" -> "भाषा"
        "app_language" -> "अॅपची भाषा"
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
        "no_results" -> "काही परिणाम सापडले नाहीत"
        "close" -> "बंद करा"
        "please_fill_all" -> "कृपया सर्व माहिती भरा."
        "passwords_not_match" -> "पासवर्ड जुळत नाहीत."
        "please_enter_login" -> "कृपया ईमेल आणि पासवर्ड प्रविष्ट करा."
        "profile_photo" -> "प्रोफाइल फोटो"
        "select_photo" -> "फोटो निवडा"
        "edit_profile" -> "प्रोफाइल संपादित करा"
        "financial_profile" -> "आर्थिक प्रोफाइल"
        "total_active_emis" -> "एकूण सक्रिय EMI"
        "total_monthly_emi" -> "एकूण मासिक EMI बांधिलकी"
        "account_information" -> "खाते माहिती"
        "account_creation_date" -> "खाते तयार केल्याची तारीख"
        "user_id" -> "वापरकर्ता ID"
        "security" -> "सुरक्षा"
        "change_password" -> "पासवर्ड बदला"
        "current_password" -> "सध्याचा पासवर्ड"
        "new_password" -> "नवीन पासवर्ड"
        "save_changes" -> "बदल जतन करा"
        "cancel" -> "रद्द करा"
        else -> englishText(key)
    }
}

fun bnText(key: String): String {
    return when (key) {
        "paywise" -> "PayWise"
        "tagline" -> "বুদ্ধিমানের মতো খরচ করুন, বিচক্ষণভাবে থাকুন।"
        "login" -> "লগইন"
        "signup" -> "নতুন অ্যাকাউন্ট তৈরি করুন"
        "create_account" -> "অ্যাকাউন্ট তৈরি করুন"
        "back_to_login" -> "লগইনে ফিরে যান"
        "create_your_account" -> "আপনার অ্যাকাউন্ট তৈরি করুন"
        "full_name" -> "পুরো নাম"
        "email" -> "ইমেইল আইডি"
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
        "valid_emi" -> "অনুগ্রহ করে সঠিক EMI তথ্য লিখুন।"
        "emi_added" -> "EMI সফলভাবে যোগ হয়েছে।"
        "no_financial_data" -> "কোনো আর্থিক তথ্য পাওয়া যায়নি।"
        "my_profile" -> "আমার প্রোফাইল"
        "not_available" -> "উপলব্ধ নয়"
        "notifications" -> "বিজ্ঞপ্তি"
        "emi_due_reminders" -> "EMI পরিশোধের অনুস্মারক"
        "payment_reminders" -> "পেমেন্ট অনুস্মারক"
        "reminder_days" -> "অনুস্মারক দিন"
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
        "search_currency" -> "মুদ্রা বা কোড অনুসন্ধান করুন"
        "select_country" -> "দেশ / অঞ্চল নির্বাচন করুন"
        "search_country" -> "দেশ অনুসন্ধান করুন"
        "select_language" -> "ভাষা নির্বাচন করুন"
        "search_language" -> "ভাষা অনুসন্ধান করুন"
        "no_results" -> "কোনো ফলাফল পাওয়া যায়নি"
        "close" -> "বন্ধ করুন"
        "please_fill_all" -> "অনুগ্রহ করে সব ঘর পূরণ করুন।"
        "passwords_not_match" -> "পাসওয়ার্ড মেলেনি।"
        "please_enter_login" -> "অনুগ্রহ করে ইমেইল এবং পাসওয়ার্ড লিখুন।"
        "profile_photo" -> "প্রোফাইল ছবি"
        "select_photo" -> "ছবি নির্বাচন করুন"
        "edit_profile" -> "প্রোফাইল সম্পাদনা করুন"
        "financial_profile" -> "আর্থিক প্রোফাইল"
        "total_active_emis" -> "মোট সক্রিয় EMI"
        "total_monthly_emi" -> "মোট মাসিক EMI প্রতিশ্রুতি"
        "account_information" -> "অ্যাকাউন্টের তথ্য"
        "account_creation_date" -> "অ্যাকাউন্ট তৈরির তারিখ"
        "user_id" -> "ব্যবহারকারী ID"
        "security" -> "নিরাপত্তা"
        "change_password" -> "পাসওয়ার্ড পরিবর্তন করুন"
        "current_password" -> "বর্তমান পাসওয়ার্ড"
        "new_password" -> "নতুন পাসওয়ার্ড"
        "save_changes" -> "পরিবর্তন সংরক্ষণ করুন"
        "cancel" -> "বাতিল করুন"
        else -> englishText(key)
    }
}

fun guText(key: String): String {
    return when (key) {
        "paywise" -> "PayWise"
        "tagline" -> "સમજદારીથી ખર્ચ કરો, સમજદારીથી જીવો."
        "login" -> "લોગિન"
        "signup" -> "નવું એકાઉન્ટ બનાવો"
        "create_account" -> "એકાઉન્ટ બનાવો"
        "back_to_login" -> "લોગિન પર પાછા જાઓ"
        "create_your_account" -> "તમારું એકાઉન્ટ બનાવો"
        "full_name" -> "પૂરું નામ"
        "email" -> "ઇમેઇલ આઈડી"
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
        "logout" -> "લોગઆઉટ"
        "welcome" -> "સ્વાગત છે"
        "manage_finances" -> "તમારી નાણાકીય બાબતો સમજદારીથી સંચાલિત કરો."
        "loading_financial_data" -> "નાણાકીય માહિતી લોડ થઈ રહી છે..."
        "monthly_salary" -> "માસિક પગાર"
        "total_emi" -> "કુલ EMI"
        "remaining_salary" -> "બાકી પગાર"
        "emi_ratio" -> "EMI ગુણોત્તર"
        "financial_status" -> "નાણાકીય સ્થિતિ"
        "your_emis" -> "તમારી EMI"
        "no_emi_records" -> "કોઈ EMI રેકોર્ડ મળ્યા નથી."
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
        "no_financial_data" -> "કોઈ નાણાકીય માહિતી ઉપલબ્ધ નથી."
        "my_profile" -> "મારી પ્રોફાઇલ"
        "not_available" -> "ઉપલબ્ધ નથી"
        "notifications" -> "સૂચનાઓ"
        "emi_due_reminders" -> "EMI બાકી હોવાના રિમાઇન્ડર્સ"
        "payment_reminders" -> "ચુકવણી રિમાઇન્ડર્સ"
        "reminder_days" -> "રિમાઇન્ડરના દિવસો"
        "days" -> "દિવસ"
        "financial_preferences" -> "નાણાકીય પસંદગીઓ"
        "currency" -> "ચલણ"
        "primary_currency" -> "મુખ્ય ચલણ"
        "show_financial_status" -> "નાણાકીય સ્થિતિ બતાવો"
        "language" -> "ભાષા"
        "app_language" -> "એપની ભાષા"
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
        "please_fill_all" -> "કૃપા કરીને બધી વિગતો ભરો."
        "passwords_not_match" -> "પાસવર્ડ મેળ ખાતા નથી."
        "please_enter_login" -> "કૃપા કરીને ઇમેઇલ અને પાસવર્ડ દાખલ કરો."
        "profile_photo" -> "પ્રોફાઇલ ફોટો"
        "select_photo" -> "ફોટો પસંદ કરો"
        "edit_profile" -> "પ્રોફાઇલ સંપાદિત કરો"
        "financial_profile" -> "નાણાકીય પ્રોફાઇલ"
        "total_active_emis" -> "કુલ સક્રિય EMI"
        "total_monthly_emi" -> "કુલ માસિક EMI પ્રતિબદ્ધતા"
        "account_information" -> "એકાઉન્ટ માહિતી"
        "account_creation_date" -> "એકાઉન્ટ બનાવ્યાની તારીખ"
        "user_id" -> "વપરાશકર્તા ID"
        "security" -> "સુરક્ષા"
        "change_password" -> "પાસવર્ડ બદલો"
        "current_password" -> "વર્તમાન પાસવર્ડ"
        "new_password" -> "નવો પાસવર્ડ"
        "save_changes" -> "ફેરફારો સાચવો"
        "cancel" -> "રદ કરો"
        else -> englishText(key)
    }
}

fun taText(key: String): String {
    return when (key) {
        "paywise" -> "PayWise"
        "tagline" -> "புத்திசாலித்தனமாக செலவு செய்யுங்கள், அறிவுடன் வாழுங்கள்."
        "login" -> "உள்நுழைவு"
        "signup" -> "புதிய கணக்கை உருவாக்கவும்"
        "create_account" -> "கணக்கை உருவாக்கவும்"
        "back_to_login" -> "உள்நுழைவுக்குத் திரும்பவும்"
        "create_your_account" -> "உங்கள் கணக்கை உருவாக்கவும்"
        "full_name" -> "முழுப் பெயர்"
        "email" -> "மின்னஞ்சல் ஐடி"
        "password" -> "கடவுச்சொல்"
        "confirm_password" -> "கடவுச்சொல்லை உறுதிப்படுத்தவும்"
        "mobile" -> "மொபைல் எண்"
        "dashboard" -> "டாஷ்போர்டு"
        "add_salary" -> "சம்பளத்தைச் சேர்க்கவும்"
        "my_emis" -> "எனது EMI"
        "add_emi" -> "EMI சேர்க்கவும்"
        "financial_summary" -> "நிதிச் சுருக்கம்"
        "profile" -> "சுயவிவரம்"
        "settings" -> "அமைப்புகள்"
        "logout" -> "வெளியேறு"
        "welcome" -> "வரவேற்கிறோம்"
        "manage_finances" -> "உங்கள் நிதியை புத்திசாலித்தனமாக நிர்வகிக்கவும்."
        "loading_financial_data" -> "நிதித் தரவு ஏற்றப்படுகிறது..."
        "monthly_salary" -> "மாத சம்பளம்"
        "total_emi" -> "மொத்த EMI"
        "remaining_salary" -> "மீதமுள்ள சம்பளம்"
        "emi_ratio" -> "EMI விகிதம்"
        "financial_status" -> "நிதி நிலை"
        "your_emis" -> "உங்கள் EMI"
        "no_emi_records" -> "EMI பதிவுகள் எதுவும் இல்லை."
        "add_update_salary" -> "சம்பளத்தைச் சேர்க்க / புதுப்பிக்கவும்"
        "save_salary" -> "சம்பளத்தைச் சேமிக்கவும்"
        "enter_valid_salary" -> "சரியான சம்பளத்தை உள்ளிடவும்."
        "salary_updated" -> "சம்பளம் வெற்றிகரமாக புதுப்பிக்கப்பட்டது."
        "bank_lender" -> "வங்கி / கடன் வழங்குபவர்"
        "emi_amount" -> "EMI தொகை"
        "due_date" -> "கடைசி தேதி"
        "frequency" -> "அதிர்வெண்"
        "monthly" -> "மாதாந்திர"
        "valid_emi" -> "சரியான EMI விவரங்களை உள்ளிடவும்."
        "emi_added" -> "EMI வெற்றிகரமாக சேர்க்கப்பட்டது."
        "no_financial_data" -> "நிதித் தரவு எதுவும் இல்லை."
        "my_profile" -> "எனது சுயவிவரம்"
        "not_available" -> "கிடைக்கவில்லை"
        "notifications" -> "அறிவிப்புகள்"
        "emi_due_reminders" -> "EMI நிலுவை நினைவூட்டல்கள்"
        "payment_reminders" -> "கட்டண நினைவூட்டல்கள்"
        "reminder_days" -> "நினைவூட்டல் நாட்கள்"
        "days" -> "நாட்கள்"
        "financial_preferences" -> "நிதி விருப்பங்கள்"
        "currency" -> "நாணயம்"
        "primary_currency" -> "முதன்மை நாணயம்"
        "show_financial_status" -> "நிதி நிலையை காட்டு"
        "language" -> "மொழி"
        "app_language" -> "பயன்பாட்டு மொழி"
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
        "profile_photo" -> "சுயவிவரப் புகைப்படம்"
        "select_photo" -> "புகைப்படத்தைத் தேர்ந்தெடுக்கவும்"
        "edit_profile" -> "சுயவிவரத்தைத் திருத்தவும்"
        "financial_profile" -> "நிதி சுயவிவரம்"
        "total_active_emis" -> "மொத்த செயலில் உள்ள EMIகள்"
        "total_monthly_emi" -> "மொத்த மாதாந்திர EMI பொறுப்பு"
        "account_information" -> "கணக்கு தகவல்"
        "account_creation_date" -> "கணக்கு உருவாக்கிய தேதி"
        "user_id" -> "பயனர் ID"
        "security" -> "பாதுகாப்பு"
        "change_password" -> "கடவுச்சொல்லை மாற்றவும்"
        "current_password" -> "தற்போதைய கடவுச்சொல்"
        "new_password" -> "புதிய கடவுச்சொல்"
        "save_changes" -> "மாற்றங்களைச் சேமிக்கவும்"
        "cancel" -> "ரத்து செய்"
        else -> englishText(key)
    }
}

fun teText(key: String): String {
    return when (key) {
        "paywise" -> "PayWise"
        "tagline" -> "తెలివిగా ఖర్చు చేయండి, వివేకంగా జీవించండి."
        "login" -> "లాగిన్"
        "signup" -> "కొత్త ఖాతాను సృష్టించండి"
        "create_account" -> "ఖాతాను సృష్టించండి"
        "back_to_login" -> "లాగిన్‌కు తిరిగి వెళ్లండి"
        "create_your_account" -> "మీ ఖాతాను సృష్టించండి"
        "full_name" -> "పూర్తి పేరు"
        "email" -> "ఇమెయిల్ ఐడి"
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
        "loading_financial_data" -> "ఆర్థిక డేటా లోడ్ అవుతోంది..."
        "monthly_salary" -> "నెలవారీ జీతం"
        "total_emi" -> "మొత్తం EMI"
        "remaining_salary" -> "మిగిలిన జీతం"
        "emi_ratio" -> "EMI నిష్పత్తి"
        "financial_status" -> "ఆర్థిక స్థితి"
        "your_emis" -> "మీ EMIలు"
        "no_emi_records" -> "EMI రికార్డులు కనుగొనబడలేదు."
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
        "no_financial_data" -> "ఆర్థిక డేటా అందుబాటులో లేదు."
        "my_profile" -> "నా ప్రొఫైల్"
        "not_available" -> "అందుబాటులో లేదు"
        "notifications" -> "నోటిఫికేషన్‌లు"
        "emi_due_reminders" -> "EMI గడువు రిమైండర్‌లు"
        "payment_reminders" -> "చెల్లింపు రిమైండర్‌లు"
        "reminder_days" -> "రిమైండర్ రోజులు"
        "days" -> "రోజులు"
        "financial_preferences" -> "ఆర్థిక ప్రాధాన్యతలు"
        "currency" -> "కరెన్సీ"
        "primary_currency" -> "ప్రధాన కరెన్సీ"
        "show_financial_status" -> "ఆర్థిక స్థితిని చూపించు"
        "language" -> "భాష"
        "app_language" -> "యాప్ భాష"
        "region" -> "ప్రాంతం"
        "country_region" -> "దేశం / ప్రాంతం"
        "about_paywise" -> "PayWise గురించి"
        "version" -> "వెర్షన్ 1.0"
        "select_currency" -> "కరెన్సీని ఎంచుకోండి"
        "search_currency" -> "కరెన్సీ లేదా కోడ్ కోసం శోధించండి"
        "select_country" -> "దేశం / ప్రాంతాన్ని ఎంచుకోండి"
        "search_country" -> "దేశం కోసం శోధించండి"
        "select_language" -> "భాషను ఎంచుకోండి"
        "search_language" -> "భాష కోసం శోధించండి"
        "no_results" -> "ఫలితాలు కనుగొనబడలేదు"
        "close" -> "మూసివేయి"
        "please_fill_all" -> "దయచేసి అన్ని ఫీల్డ్‌లను పూరించండి."
        "passwords_not_match" -> "పాస్‌వర్డ్‌లు సరిపోలలేదు."
        "please_enter_login" -> "దయచేసి ఇమెయిల్ మరియు పాస్‌వర్డ్ నమోదు చేయండి."
        "profile_photo" -> "ప్రొఫైల్ ఫోటో"
        "select_photo" -> "ఫోటోను ఎంచుకోండి"
        "edit_profile" -> "ప్రొఫైల్‌ను సవరించండి"
        "financial_profile" -> "ఆర్థిక ప్రొఫైల్"
        "total_active_emis" -> "మొత్తం క్రియాశీల EMIలు"
        "total_monthly_emi" -> "మొత్తం నెలవారీ EMI బాధ్యత"
        "account_information" -> "ఖాతా సమాచారం"
        "account_creation_date" -> "ఖాతా సృష్టించిన తేదీ"
        "user_id" -> "వినియోగదారు ID"
        "security" -> "భద్రత"
        "change_password" -> "పాస్‌వర్డ్ మార్చండి"
        "current_password" -> "ప్రస్తుత పాస్‌వర్డ్"
        "new_password" -> "కొత్త పాస్‌వర్డ్"
        "save_changes" -> "మార్పులను సేవ్ చేయండి"
        "cancel" -> "రద్దు చేయండి"
        else -> englishText(key)
    }
}

fun esText(key: String): String {
    return when (key) {
        "paywise" -> "PayWise"
        "tagline" -> "Gasta de forma inteligente, vive con sabiduría."
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
        "not_available" -> "No disponible"
        "notifications" -> "Notificaciones"
        "emi_due_reminders" -> "Recordatorios de EMI"
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
        else -> englishText(key)
    }
}

fun frText(key: String): String {
    return when (key) {
        "paywise" -> "PayWise"
        "tagline" -> "Dépensez intelligemment, vivez sagement."
        "login" -> "Connexion"
        "signup" -> "Créer un nouveau compte"
        "create_account" -> "Créer un compte"
        "back_to_login" -> "Retour à la connexion"
        "create_your_account" -> "Créez votre compte"
        "full_name" -> "Nom complet"
        "email" -> "Adresse e-mail"
        "password" -> "Mot de passe"
        "confirm_password" -> "Confirmer le mot de passe"
        "mobile" -> "Numéro de mobile"
        "dashboard" -> "Tableau de bord"
        "add_salary" -> "Ajouter un salaire"
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
        "bank_lender" -> "Banque / Prêteur"
        "emi_amount" -> "Montant EMI"
        "due_date" -> "Date d'échéance"
        "frequency" -> "Fréquence"
        "monthly" -> "Mensuel"
        "valid_emi" -> "Veuillez entrer des détails EMI valides."
        "emi_added" -> "EMI ajoutée avec succès."
        "no_financial_data" -> "Aucune donnée financière disponible."
        "my_profile" -> "Mon profil"
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
        "please_enter_login" -> "Veuillez saisir l'e-mail et le mot de passe."
        "profile_photo" -> "Photo de profil"
        "select_photo" -> "Sélectionner une photo"
        "edit_profile" -> "Modifier le profil"
        "financial_profile" -> "Profil financier"
        "total_active_emis" -> "Total des EMI actives"
        "total_monthly_emi" -> "Engagement total des EMI mensuelles"
        "account_information" -> "Informations du compte"
        "account_creation_date" -> "Date de création du compte"
        "user_id" -> "ID utilisateur"
        "security" -> "Sécurité"
        "change_password" -> "Changer le mot de passe"
        "current_password" -> "Mot de passe actuel"
        "new_password" -> "Nouveau mot de passe"
        "save_changes" -> "Enregistrer les modifications"
        "cancel" -> "Annuler"
        else -> englishText(key)
    }
}

fun deText(key: String): String {
    return when (key) {
        "paywise" -> "PayWise"
        "tagline" -> "Clever ausgeben, weise leben."
        "login" -> "Anmelden"
        "signup" -> "Neues Konto erstellen"
        "create_account" -> "Konto erstellen"
        "back_to_login" -> "Zurück zur Anmeldung"
        "create_your_account" -> "Erstellen Sie Ihr Konto"
        "full_name" -> "Vollständiger Name"
        "email" -> "E-Mail-ID"
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
        "manage_finances" -> "Verwalten Sie Ihre Finanzen intelligent."
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
        "not_available" -> "Nicht verfügbar"
        "notifications" -> "Benachrichtigungen"
        "emi_due_reminders" -> "EMI-Fälligkeitserinnerungen"
        "payment_reminders" -> "Zahlungserinnerungen"
        "reminder_days" -> "Erinnerungstage"
        "days" -> "Tage"
        "financial_preferences" -> "Finanzielle Einstellungen"
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
        "passwords_not_match" -> "Passwörter stimmen nicht überein."
        "please_enter_login" -> "Bitte E-Mail und Passwort eingeben."
        "profile_photo" -> "Profilfoto"
        "select_photo" -> "Foto auswählen"
        "edit_profile" -> "Profil bearbeiten"
        "financial_profile" -> "Finanzprofil"
        "total_active_emis" -> "Aktive EMIs insgesamt"
        "total_monthly_emi" -> "Gesamte monatliche EMI-Verpflichtung"
        "account_information" -> "Kontoinformationen"
        "account_creation_date" -> "Kontoerstellungsdatum"
        "user_id" -> "Benutzer-ID"
        "security" -> "Sicherheit"
        "change_password" -> "Passwort ändern"
        "current_password" -> "Aktuelles Passwort"
        "new_password" -> "Neues Passwort"
        "save_changes" -> "Änderungen speichern"
        "cancel" -> "Abbrechen"
        else -> englishText(key)
    }
}

fun jaText(key: String): String {
    return when (key) {
        "paywise" -> "PayWise"
        "tagline" -> "賢く使って、賢く暮らそう。"
        "login" -> "ログイン"
        "signup" -> "新しいアカウントを作成"
        "create_account" -> "アカウントを作成"
        "back_to_login" -> "ログインに戻る"
        "create_your_account" -> "アカウントを作成してください"
        "full_name" -> "氏名"
        "email" -> "メールID"
        "password" -> "パスワード"
        "confirm_password" -> "パスワードを確認"
        "mobile" -> "携帯電話番号"
        "dashboard" -> "ダッシュボード"
        "add_salary" -> "給与を追加"
        "my_emis" -> "自分のEMI"
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
        "no_emi_records" -> "EMI記録がありません。"
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
        "not_available" -> "利用できません"
        "notifications" -> "通知"
        "emi_due_reminders" -> "EMI支払期日のリマインダー"
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
        "total_active_emis" -> "有効なEMIの合計"
        "total_monthly_emi" -> "月間EMI負担額の合計"
        "account_information" -> "アカウント情報"
        "account_creation_date" -> "アカウント作成日"
        "user_id" -> "ユーザーID"
        "security" -> "セキュリティ"
        "change_password" -> "パスワードを変更"
        "current_password" -> "現在のパスワード"
        "new_password" -> "新しいパスワード"
        "save_changes" -> "変更を保存"
        "cancel" -> "キャンセル"
        else -> englishText(key)
    }
}

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
        "my_emis" -> "我的EMI"
        "add_emi" -> "添加EMI"
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
        "your_emis" -> "您的EMI"
        "no_emi_records" -> "未找到EMI记录。"
        "add_update_salary" -> "添加 / 更新工资"
        "save_salary" -> "保存工资"
        "enter_valid_salary" -> "请输入有效工资。"
        "salary_updated" -> "工资更新成功。"
        "bank_lender" -> "银行 / 贷款机构"
        "emi_amount" -> "EMI金额"
        "due_date" -> "到期日"
        "frequency" -> "频率"
        "monthly" -> "每月"
        "valid_emi" -> "请输入有效的EMI信息。"
        "emi_added" -> "EMI添加成功。"
        "no_financial_data" -> "没有可用的财务数据。"
        "my_profile" -> "我的资料"
        "not_available" -> "不可用"
        "notifications" -> "通知"
        "emi_due_reminders" -> "EMI到期提醒"
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
        "about_paywise" -> "关于PayWise"
        "version" -> "版本1.0"
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
        else -> englishText(key)
    }
}

fun arText(key: String): String {
    return when (key) {
        "paywise" -> "PayWise"
        "tagline" -> "أنفق بذكاء، وعش بحكمة."
        "login" -> "تسجيل الدخول"
        "signup" -> "إنشاء حساب جديد"
        "create_account" -> "إنشاء حساب"
        "back_to_login" -> "العودة إلى تسجيل الدخول"
        "create_your_account" -> "أنشئ حسابك"
        "full_name" -> "الاسم الكامل"
        "email" -> "معرّف البريد الإلكتروني"
        "password" -> "كلمة المرور"
        "confirm_password" -> "تأكيد كلمة المرور"
        "mobile" -> "رقم الهاتف المحمول"
        "dashboard" -> "لوحة التحكم"
        "add_salary" -> "إضافة الراتب"
        "my_emis" -> "أقساط EMI الخاصة بي"
        "add_emi" -> "إضافة EMI"
        "financial_summary" -> "الملخص المالي"
        "profile" -> "الملف الشخصي"
        "settings" -> "الإعدادات"
        "logout" -> "تسجيل الخروج"
        "welcome" -> "مرحباً"
        "manage_finances" -> "أدر أموالك بذكاء."
        "loading_financial_data" -> "جارٍ تحميل البيانات المالية..."
        "monthly_salary" -> "الراتب الشهري"
        "total_emi" -> "إجمالي EMI"
        "remaining_salary" -> "الراتب المتبقي"
        "emi_ratio" -> "نسبة EMI"
        "financial_status" -> "الحالة المالية"
        "your_emis" -> "أقساط EMI الخاصة بك"
        "no_emi_records" -> "لم يتم العثور على سجلات EMI."
        "add_update_salary" -> "إضافة / تحديث الراتب"
        "save_salary" -> "حفظ الراتب"
        "enter_valid_salary" -> "أدخل راتباً صالحاً."
        "salary_updated" -> "تم تحديث الراتب بنجاح."
        "bank_lender" -> "البنك / المقرض"
        "emi_amount" -> "مبلغ EMI"
        "due_date" -> "تاريخ الاستحقاق"
        "frequency" -> "التكرار"
        "monthly" -> "شهري"
        "valid_emi" -> "يرجى إدخال تفاصيل EMI صحيحة."
        "emi_added" -> "تمت إضافة EMI بنجاح."
        "no_financial_data" -> "لا توجد بيانات مالية متاحة."
        "my_profile" -> "ملفي الشخصي"
        "not_available" -> "غير متوفر"
        "notifications" -> "الإشعارات"
        "emi_due_reminders" -> "تذكيرات استحقاق EMI"
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
        "search_currency" -> "ابحث عن العملة أو الرمز"
        "select_country" -> "اختر الدولة / المنطقة"
        "search_country" -> "ابحث عن الدولة"
        "select_language" -> "اختر اللغة"
        "search_language" -> "ابحث عن اللغة"
        "no_results" -> "لم يتم العثور على نتائج"
        "close" -> "إغلاق"
        "please_fill_all" -> "يرجى ملء جميع الحقول."
        "passwords_not_match" -> "كلمتا المرور غير متطابقتين."
        "please_enter_login" -> "يرجى إدخال البريد الإلكتروني وكلمة المرور."
        "profile_photo" -> "صورة الملف الشخصي"
        "select_photo" -> "اختيار صورة"
        "edit_profile" -> "تعديل الملف الشخصي"
        "financial_profile" -> "الملف المالي"
        "total_active_emis" -> "إجمالي أقساط EMI النشطة"
        "total_monthly_emi" -> "إجمالي التزام EMI الشهري"
        "account_information" -> "معلومات الحساب"
        "account_creation_date" -> "تاريخ إنشاء الحساب"
        "user_id" -> "معرّف المستخدم"
        "security" -> "الأمان"
        "change_password" -> "تغيير كلمة المرور"
        "current_password" -> "كلمة المرور الحالية"
        "new_password" -> "كلمة المرور الجديدة"
        "save_changes" -> "حفظ التغييرات"
        "cancel" -> "إلغاء"
        else -> englishText(key)
    }
}

fun koText(key: String): String {
    return when (key) {
        "paywise" -> "PayWise"
        "tagline" -> "현명하게 쓰고, 지혜롭게 생활하세요."
        "login" -> "로그인"
        "signup" -> "새 계정 만들기"
        "create_account" -> "계정 만들기"
        "back_to_login" -> "로그인으로 돌아가기"
        "create_your_account" -> "계정을 만들어 주세요"
        "full_name" -> "전체 이름"
        "email" -> "이메일 ID"
        "password" -> "비밀번호"
        "confirm_password" -> "비밀번호 확인"
        "mobile" -> "휴대폰 번호"
        "dashboard" -> "대시보드"
        "add_salary" -> "급여 추가"
        "my_emis" -> "내 EMI"
        "add_emi" -> "EMI 추가"
        "financial_summary" -> "재정 요약"
        "profile" -> "프로필"
        "settings" -> "설정"
        "logout" -> "로그아웃"
        "welcome" -> "환영합니다"
        "manage_finances" -> "재정을 현명하게 관리하세요."
        "loading_financial_data" -> "재정 데이터를 불러오는 중..."
        "monthly_salary" -> "월급"
        "total_emi" -> "총 EMI"
        "remaining_salary" -> "남은 급여"
        "emi_ratio" -> "EMI 비율"
        "financial_status" -> "재정 상태"
        "your_emis" -> "내 EMI"
        "no_emi_records" -> "EMI 기록이 없습니다."
        "add_update_salary" -> "급여 추가 / 업데이트"
        "save_salary" -> "급여 저장"
        "enter_valid_salary" -> "유효한 급여를 입력하세요."
        "salary_updated" -> "급여가 성공적으로 업데이트되었습니다."
        "bank_lender" -> "은행 / 대출기관"
        "emi_amount" -> "EMI 금액"
        "due_date" -> "납부일"
        "frequency" -> "빈도"
        "monthly" -> "매월"
        "valid_emi" -> "올바른 EMI 정보를 입력하세요."
        "emi_added" -> "EMI가 성공적으로 추가되었습니다."
        "no_financial_data" -> "재정 데이터가 없습니다."
        "my_profile" -> "내 프로필"
        "not_available" -> "사용할 수 없음"
        "notifications" -> "알림"
        "emi_due_reminders" -> "EMI 납부 알림"
        "payment_reminders" -> "결제 알림"
        "reminder_days" -> "알림 날짜"
        "days" -> "일"
        "financial_preferences" -> "재정 설정"
        "currency" -> "통화"
        "primary_currency" -> "기본 통화"
        "show_financial_status" -> "재정 상태 표시"
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
        "edit_profile" -> "프로필 편집"
        "financial_profile" -> "재정 프로필"
        "total_active_emis" -> "총 활성 EMI"
        "total_monthly_emi" -> "총 월간 EMI 부담액"
        "account_information" -> "계정 정보"
        "account_creation_date" -> "계정 생성일"
        "user_id" -> "사용자 ID"
        "security" -> "보안"
        "change_password" -> "비밀번호 변경"
        "current_password" -> "현재 비밀번호"
        "new_password" -> "새 비밀번호"
        "save_changes" -> "변경사항 저장"
        "cancel" -> "취소"
        else -> englishText(key)
    }
}

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
        "manage_finances" ->
            "Manage your finances wisely."
        "loading_financial_data" ->
            "Loading financial data..."

        "monthly_salary" -> "Monthly Salary"
        "total_emi" -> "Total EMI"
        "remaining_salary" -> "Remaining Salary"
        "emi_ratio" -> "EMI Ratio"
        "financial_status" -> "Financial Status"
        "your_emis" -> "Your EMIs"

        "no_emi_records" ->
            "No EMI records found."

        "add_update_salary" ->
            "Add / Update Salary"
        "save_salary" -> "Save Salary"
        "enter_valid_salary" ->
            "Enter a valid salary."
        "salary_updated" ->
            "Salary updated successfully."

        "bank_lender" -> "Bank / Lender"
        "emi_amount" -> "EMI Amount"
        "due_date" -> "Due Date"
        "frequency" -> "Frequency"
        "monthly" -> "Monthly"

        "valid_emi" ->
            "Please enter valid EMI details."
        "emi_added" ->
            "EMI added successfully."

        "no_financial_data" ->
            "No financial data available."

        "my_profile" -> "My Profile"
        "profile_information" -> "Profile Information"
        "personal_information" -> "Personal Information"
        "not_available" -> "Not available"

        "notifications" -> "Notifications"
        "emi_due_reminders" ->
            "EMI Due Reminders"
        "payment_reminders" ->
            "Payment Reminders"
        "reminder_days" ->
            "Reminder Days"
        "days" -> "days"

        "financial_preferences" ->
            "Financial Preferences"
        "currency" -> "Currency"
        "primary_currency" ->
            "Primary Currency"
        "show_financial_status" ->
            "Show Financial Status"

        "language" -> "Language"
        "app_language" -> "App Language"

        "region" -> "Region"
        "country_region" -> "Country / Region"

        "about_paywise" -> "About PayWise"
        "version" -> "Version 1.0"

        "select_currency" ->
            "Select Currency"
        "search_currency" ->
            "Search currency or code"

        "select_country" ->
            "Select Country / Region"
        "search_country" ->
            "Search country"

        "select_language" ->
            "Select Language"
        "search_language" ->
            "Search language"

        "no_results" -> "No results found"
        "close" -> "Close"

        "please_fill_all" ->
            "Please fill all fields."
        "passwords_not_match" ->
            "Passwords do not match."
        "please_enter_login" ->
            "Please enter email and password."

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
        "everyday_expenses" -> "Everyday Expenses"
        "track_daily_spending" -> "Track your daily spending, analyze it, and save more."
        "add_expense" -> "Add Expense"
        "amount" -> "Amount"
        "category" -> "Category"
        "date" -> "Date"
        "note_optional" -> "Note (optional)"
        "food" -> "Food"
        "travel" -> "Travel"
        "shopping" -> "Shopping"
        "bills" -> "Bills"
        "entertainment" -> "Entertainment"
        "other" -> "Other"
        "monthly_overview" -> "Monthly Overview"
        "total_spent" -> "Total Spent"
        "category_breakdown" -> "Category-wise Breakdown"
        "recent_expenses" -> "Recent Expenses"
        "what_should_i_cut" -> "What Should I Cut?"
        "top_suggestions" -> "Top Suggestions"
        "potential_monthly_savings" -> "Potential Monthly Savings"
        "spending_limits" -> "Spending Limits"
        "set_spending_limit" -> "Set Spending Limit"
        "current_limit" -> "Current Limit"
        "smart_alerts" -> "Smart Alerts"
        "financial_health_tips" -> "Financial Health Tips"
        "summary" -> "Summary"
        "available_money" -> "Available Money"
        "save_expense" -> "Save Expense"
        "cancel" -> "Cancel"
        "set_limit" -> "Set Limit"
        "limit_amount" -> "Limit Amount"
        "no_expenses" -> "No expenses added yet."
        "expense_added" -> "Expense added successfully."
        "high_spending" -> "High spending"
        "approaching_limit" -> "Approaching limit"
        "build_emergency_fund" -> "Build an emergency fund"
        "monitor_recurring" -> "Monitor recurring expenses"
        "avoid_new_emi" -> "Avoid excessive new EMIs"
        "set_savings_goal" -> "Set a savings goal"
        "cut" -> "Cut"
        "suggested" -> "Suggested"
        "spent" -> "Spent"
        "save_more" -> "Save more"

        else -> key
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
            Currency.getInstance(selectedCurrencyCode).getSymbol(Locale.ENGLISH)
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

            "dashboard" -> {

                DashboardScreen(
                    userId = userId,
                    userName = userName,
                    userEmail = userEmail,
                    userMobile = userMobile,
                    languageCode = selectedLanguageCode,

                    onLanguageChanged = {
                        selectedLanguageCode = it
                    },

                    onCurrencyChanged = {
                        selectedCurrencyCode = it
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

                    message =
                        tr(
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

                    message =
                        tr(
                            "please_fill_all",
                            languageCode
                        )

                    return@Button
                }

                if (password != confirmPassword) {

                    message =
                        tr(
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
    onLanguageChanged: (String) -> Unit,
    onCurrencyChanged: (String) -> Unit,
    onLogout: () -> Unit
) {

    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    val scope = rememberCoroutineScope()

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

    // Everyday Expenses data is kept per logged-in user during the current app session.
    var expenses by remember {
        mutableStateOf<List<Expense>>(emptyList())
    }

    var spendingLimits by remember {
        mutableStateOf<Map<String, Double>>(emptyMap())
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
                    "Profile",
                    "Settings"
                )

                menuItems.forEach { item ->

                    val translatedItem = when (item) {

                        "Dashboard" ->
                            tr(
                                "dashboard",
                                languageCode
                            )

                        "Add Salary" ->
                            tr(
                                "add_salary",
                                languageCode
                            )

                        "My EMIs" ->
                            tr(
                                "my_emis",
                                languageCode
                            )

                        "Add EMI" ->
                            tr(
                                "add_emi",
                                languageCode
                            )

                        "Financial Summary" ->
                            tr(
                                "financial_summary",
                                languageCode
                            )

                        "Everyday Expenses" ->
                            tr(
                                "everyday_expenses",
                                languageCode
                            )

                        "Profile" ->
                            tr(
                                "profile",
                                languageCode
                            )

                        "Settings" ->
                            tr(
                                "settings",
                                languageCode
                            )

                        else -> item
                    }

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
                                selectedContainerColor =
                                    LightBlue,
                                selectedTextColor =
                                    DeepBlue,
                                unselectedTextColor =
                                    DarkText
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

                                    if (
                                        drawerState.isClosed
                                    ) {
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

                    "Dashboard" -> {

                        DashboardHome(
                            userName = userName,
                            emiResponse = emiResponse,
                            loading = loading,
                            message = message,
                            languageCode = languageCode,
                            reminderDays = reminderDays,
                            onReminderDaysChanged = { reminderDays = it }
                        )
                    }

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

                    "My EMIs" -> {

                        MyEMIsScreen(
                            emiData =
                                emiResponse?.emiData
                                    ?: emptyList(),

                            languageCode = languageCode
                        )
                    }

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

                    "Financial Summary" -> {

                        FinancialSummaryScreen(
                            response = emiResponse,
                            languageCode = languageCode
                        )
                    }

                    "Everyday Expenses" -> {

                        EverydayExpensesScreen(
                            expenses = expenses,
                            spendingLimits = spendingLimits,
                            salary = emiResponse?.salary ?: 0.0,
                            totalEmi = emiResponse?.totalEmi ?: 0.0,
                            languageCode = languageCode,
                            onAddExpense = { expense ->
                                expenses = expenses + expense
                            },
                            onSetLimit = { category, limit ->
                                spendingLimits = spendingLimits + (category to limit)
                            }
                        )
                    }

                    "Profile" -> {

                        ProfileScreen(
                            userId = userId,
                            userName = userName,
                            userEmail = userEmail,
                            userMobile = userMobile,
                            emiResponse = emiResponse,
                            languageCode = languageCode
                        )
                    }

                    "Settings" -> {

                        SettingsScreen(
                            selectedLanguageCode =
                                languageCode,
                            selectedCurrencyCode =
                                LocalPayWiseCurrency.current.code,
                            selectedReminderDays = reminderDays,
                            onLanguageChanged = {
                                onLanguageChanged(it)
                            },
                            onCurrencyChanged = {
                                onCurrencyChanged(it)
                            },
                            onReminderDaysChanged = {
                                reminderDays = it
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
    languageCode: String
) {

    val context = LocalContext.current

    var profilePhotoUri by rememberSaveable {
        mutableStateOf("")
    }

    var showEditProfile by remember {
        mutableStateOf(false)
    }

    var showChangePassword by remember {
        mutableStateOf(false)
    }

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
            }
            profilePhotoUri = uri.toString()
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundWhite)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        item {
            Text(
                text = tr("my_profile", languageCode),
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = DeepBlue
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = tr("profile_information", languageCode),
                fontSize = 15.sp,
                color = GreyText
            )
        }

        item {
            ProfileSectionCard(
                title = tr("profile_photo", languageCode)
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    if (profilePhotoUri.isNotBlank()) {
                        val bitmap = remember(profilePhotoUri) {
                            try {
                                context.contentResolver.openInputStream(
                                    android.net.Uri.parse(profilePhotoUri)
                                )?.use { BitmapFactory.decodeStream(it) }
                            } catch (_: Exception) {
                                null
                            }
                        }

                        if (bitmap != null) {
                            Image(
                                bitmap = bitmap.asImageBitmap(),
                                contentDescription = tr("profile_photo", languageCode),
                                modifier = Modifier
                                    .size(110.dp)
                                    .clip(CircleShape)
                                    .border(2.dp, PrimaryBlue, CircleShape)
                            )
                        } else {
                            ProfileInitial(userName)
                        }
                    } else {
                        ProfileInitial(userName)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                TextButton(
                    onClick = {
                        photoPicker.launch(arrayOf("image/*"))
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = tr("select_photo", languageCode),
                        color = PrimaryBlue
                    )
                }
            }
        }

        item {
            ProfileSectionCard(
                title = tr("personal_information", languageCode)
            ) {
                ProfileDataRow(
                    tr("full_name", languageCode),
                    if (userName.isNotBlank()) userName else tr("not_available", languageCode)
                )
                ProfileDataRow(
                    tr("email", languageCode),
                    if (userEmail.isNotBlank()) userEmail else tr("not_available", languageCode)
                )
                ProfileDataRow(
                    tr("mobile", languageCode),
                    if (userMobile.isNotBlank()) userMobile else tr("not_available", languageCode)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { showEditProfile = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryBlue
                    )
                ) {
                    Text(
                        text = tr("edit_profile", languageCode),
                        color = Color.White
                    )
                }
            }
        }

        item {
            ProfileSectionCard(
                title = tr("financial_profile", languageCode)
            ) {
                ProfileDataRow(
                    tr("monthly_salary", languageCode),
                    emiResponse?.let { formatPayWiseMoney(it.salary) }
                        ?: tr("not_available", languageCode)
                )
                ProfileDataRow(
                    tr("total_active_emis", languageCode),
                    emiResponse?.emiData?.size?.toString()
                        ?: tr("not_available", languageCode)
                )
                ProfileDataRow(
                    tr("total_monthly_emi", languageCode),
                    emiResponse?.let { formatPayWiseMoney(it.totalEmi) }
                        ?: tr("not_available", languageCode)
                )
            }
        }

        item {
            ProfileSectionCard(
                title = tr("account_information", languageCode)
            ) {
                ProfileDataRow(
                    tr("account_creation_date", languageCode),
                    tr("not_available", languageCode)
                )
                ProfileDataRow(
                    tr("user_id", languageCode),
                    userId.toString()
                )
            }
        }

        item {
            ProfileSectionCard(
                title = tr("security", languageCode)
            ) {
                Button(
                    onClick = { showChangePassword = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Orange
                    )
                ) {
                    Text(
                        text = tr("change_password", languageCode),
                        color = Color.White
                    )
                }
            }
        }
    }

    if (showEditProfile) {
        EditProfileDialog(
            userName = userName,
            userEmail = userEmail,
            userMobile = userMobile,
            languageCode = languageCode,
            onDismiss = { showEditProfile = false }
        )
    }

    if (showChangePassword) {
        ChangePasswordDialog(
            languageCode = languageCode,
            onDismiss = { showChangePassword = false }
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
    userName: String,
    userEmail: String,
    userMobile: String,
    languageCode: String,
    onDismiss: () -> Unit
) {
    var name by rememberSaveable { mutableStateOf(userName) }
    var email by rememberSaveable { mutableStateOf(userEmail) }
    var mobile by rememberSaveable { mutableStateOf(userMobile) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(tr("edit_profile", languageCode))
        },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(tr("full_name", languageCode)) },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(tr("email", languageCode)) },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = mobile,
                    onValueChange = { mobile = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(tr("mobile", languageCode)) },
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