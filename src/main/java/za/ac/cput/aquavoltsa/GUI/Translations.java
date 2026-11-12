//Final update done by Inathi and Oyama
package za.ac.cput.aquavoltsa.GUI;

import java.util.HashMap;
import java.util.Map;

/**
 * Translates UI text based on the language saved in SettingsStore for the logged-in user.
 */
public class Translations {

    // A map where Key = "language", and Value = Map of "key -> translated string"
    private static final Map<String, Map<String, String>> DICTIONARY = new HashMap<>();

    static {
        // ---------------------------------------------------------
        // 1. ENGLISH (Default)
        // ---------------------------------------------------------
        Map<String, String> en = new HashMap<>();
        en.put("settings", "Settings");
        en.put("account", "Account");
        en.put("profile_settings", "Profile Settings");
        en.put("terms_conditions", "Terms & Conditions");
        en.put("notifications", "Notifications");
        en.put("notif_pref", "Notification Preference");
        en.put("water_alerts", "Water Alerts");
        en.put("elec_alerts", "Electricity Alerts");
        en.put("app_pref", "App Preference");
        en.put("language", "Language");
        en.put("help_support", "Help & Support");
        en.put("about", "About AquaVolt");
        en.put("logout", "LogOut");
        en.put("confirm_logout", "Are you sure you want to log out?");
        en.put("water", "Water");
        en.put("electricity", "Electricity");
        en.put("water_hist", "Water Usage History");
        en.put("elec_hist", "Electricity Usage History");
        en.put("pay_water", "Pay Water Bills");
        en.put("buy_elec", "Buy Electricity");
        en.put("water_hist_btn", "Water history");
        en.put("elec_hist_btn", "Electricity history");
        en.put("exit", "Exit");
        en.put("alerts", "Alerts");
        en.put("username", "Username");
        en.put("meter_number", "Meter Number");
        en.put("address", "Address");
        en.put("select_amount", "Select Amount");
        en.put("custom", "CUSTOM");
        en.put("continue", "CONTINUE");
        en.put("back", "BACK");
        en.put("enter_meter", "Enter your Meter Number");
        en.put("meter_empty", "Meter number field is empty");
        en.put("meter_digits", "Meter Number should contain only Digits!");
        en.put("enter_address", "Enter your Address");
        en.put("address_empty", "Address field is empty");
        en.put("choose_amount", "Choose or enter your Amount!");
        en.put("select_one", "Select one Toggle Button");
        en.put("enter_amount", "Enter your amount");
        en.put("amount_empty", "Amount cannot be empty");
        en.put("min_purchase", "Minimum purchase is R50");
        en.put("amount_less", "Amount cannot be less than R50");
        en.put("amount_digits", "Amount should contain only Digits");
        
        // New keys for Terms
        en.put("terms_title", "Terms and Conditions");
        en.put("terms_sec1", "1. General Provisions");
        en.put("terms_sec2", "2. Customer Terms");
        en.put("terms_sec3", "3. Business Team");
        en.put("terms_sec4", "4. Municipality & Eskom Terms");
        en.put("terms_sec5", "5. Legal & Compliance");
        
        // New keys for Payment Success
        en.put("payment_successful", "Payment Successful");
        en.put("purchase_successful", "Your purchase was successful");
        en.put("your_token", "Your Token");
        en.put("transaction_id", "Transaction ID");
        en.put("copy_id", "Copy ID");
        en.put("back_home", "Back to Home");
        en.put("view_history", "View History");
        en.put("thank_you", "Thank you for using our services");
        DICTIONARY.put("English", en);

        // ---------------------------------------------------------
        // 2. AFRIKAANS
        // ---------------------------------------------------------
        Map<String, String> af = new HashMap<>();
        af.put("settings", "Instellings");
        af.put("account", "Rekening");
        af.put("profile_settings", "Profiel Instellings");
        af.put("terms_conditions", "Bepalings & Voorwaardes");
        af.put("notifications", "Kennisgewings");
        af.put("notif_pref", "Kennisgewing Voorkeur");
        af.put("water_alerts", "Water Waarskuwings");
        af.put("elec_alerts", "Elektrisiteit Waarskuwings");
        af.put("app_pref", "Toepassing Voorkeur");
        af.put("language", "Taal");
        af.put("help_support", "Hulp & Ondersteuning");
        af.put("about", "Oor AquaVolt");
        af.put("logout", "Teken Uit");
        af.put("confirm_logout", "Is jy seker jy wil uitteken?");
        af.put("water", "Water");
        af.put("electricity", "Elektrisiteit");
        af.put("water_hist", "Water Verbruik Geskiedenis");
        af.put("elec_hist", "Elektrisiteit Verbruik Geskiedenis");
        af.put("pay_water", "Betaal Water Rekeninge");
        af.put("buy_elec", "Koop Elektrisiteit");
        af.put("water_hist_btn", "Water geskiedenis");
        af.put("elec_hist_btn", "Elektrisiteit geskiedenis");
        af.put("exit", "Verlaat");
        af.put("alerts", "Waarskuwings");
        af.put("username", "Gebruikersnaam");
        af.put("meter_number", "Meternommer");
        af.put("address", "Adres");
        af.put("select_amount", "Kies Bedrag");
        af.put("custom", "PASGEMAAK");
        af.put("continue", "GAAN VOORT");
        af.put("back", "TERUG");
        af.put("enter_meter", "Voer jou Meternommer in");
        af.put("meter_empty", "Meternommer veld is leeg");
        af.put("meter_digits", "Meternommer moet slegs syfers bevat!");
        af.put("enter_address", "Voer jou Adres in");
        af.put("address_empty", "Adres veld is leeg");
        af.put("choose_amount", "Kies of voer jou Bedrag in!");
        af.put("select_one", "Kies een Knoppie");
        af.put("enter_amount", "Voer jou bedrag in");
        af.put("amount_empty", "Bedrag kan nie leeg wees nie");
        af.put("min_purchase", "Minimum aankoop is R50");
        af.put("amount_less", "Bedrag kan nie minder as R50 wees nie");
        af.put("amount_digits", "Bedrag moet slegs syfers bevat");
        
        af.put("terms_title", "Bepalings en Voorwaardes");
        af.put("terms_sec1", "1. Algemene Bepalings");
        af.put("terms_sec2", "2. Klant Bepalings");
        af.put("terms_sec3", "3. Besigheidspan");
        af.put("terms_sec4", "4. Munisipaliteit & Eskom Bepalings");
        af.put("terms_sec5", "5. Regs & Nakoming");
        
        af.put("payment_successful", "Betaling Suksesvol");
        af.put("purchase_successful", "Jou aankoop was suksesvol");
        af.put("your_token", "Jou Token");
        af.put("transaction_id", "Transaksie ID");
        af.put("copy_id", "Kopieer ID");
        af.put("back_home", "Terug na Tuis");
        af.put("view_history", "Sien Geskiedenis");
        af.put("thank_you", "Dankie dat jy ons dienste gebruik");
        DICTIONARY.put("Afrikaans", af);

        // ---------------------------------------------------------
        // 3. ISIZULU
        // ---------------------------------------------------------
        Map<String, String> zu = new HashMap<>();
        zu.put("settings", "Izilungiselelo");
        zu.put("account", "I-akhawunti");
        zu.put("profile_settings", "Izilungiselelo Zephrofayili");
        zu.put("terms_conditions", "Imigomo Nemibandela");
        zu.put("notifications", "Izaziso");
        zu.put("notif_pref", "Okuthandwa Izaziso");
        zu.put("water_alerts", "Izexwayiso Zamanzi");
        zu.put("elec_alerts", "Izexwayiso Zikagesi");
        zu.put("app_pref", "Okuthandwa Uhlelo");
        zu.put("language", "Ulimi");
        zu.put("help_support", "Usizo Nosekelo");
        zu.put("about", "Mayelana ne-AquaVolt");
        zu.put("logout", "Phuma");
        zu.put("confirm_logout", "Uqinisekile ukuthi ufuna ukuphuma?");
        zu.put("water", "Amanzi");
        zu.put("electricity", "Ugesi");
        zu.put("water_hist", "Umlando Wokusetshenziswa Kwamanzi");
        zu.put("elec_hist", "Umlando Wokusetshenziswa Kukagesi");
        zu.put("pay_water", "Khokha Izikweletu Zamanzi");
        zu.put("buy_elec", "Thenga Ugesi");
        zu.put("water_hist_btn", "Umlando wamanzi");
        zu.put("elec_hist_btn", "Umlando kagesi");
        zu.put("exit", "Phuma");
        zu.put("alerts", "Izexwayiso");
        zu.put("username", "Igama lomsebenzisi");
        zu.put("meter_number", "Inombolo Yemitha");
        zu.put("address", "Ikheli");
        zu.put("select_amount", "Khetha Inani");
        zu.put("custom", "OKWAKHO");
        zu.put("continue", "QHUBEKA");
        zu.put("back", "EMUVA");
        zu.put("enter_meter", "Faka Inombolo Yakho Yemitha");
        zu.put("meter_empty", "Inkambu yenombolo yemitha ayinakho okubhaliwe");
        zu.put("meter_digits", "Inombolo yemitha kufanele ibe nezinombolo kuphela!");
        zu.put("enter_address", "Faka Ikheli Lakho");
        zu.put("address_empty", "Inkambu yekheli ayinakho okubhaliwe");
        zu.put("choose_amount", "Khetha noma ufake Inani Lakho!");
        zu.put("select_one", "Khetha Inkinobho eyodwa");
        zu.put("enter_amount", "Faka inani lakho");
        zu.put("amount_empty", "Inani alikwazi ukuba lize");
        zu.put("min_purchase", "Ukuthenga okuncane ngu-R50");
        zu.put("amount_less", "Inani alikwazi ukuba ngaphansi kuka-R50");
        zu.put("amount_digits", "Inani kufanele libe nezinombolo kuphela");
        
        zu.put("terms_title", "Imigomo Nemibandela");
        zu.put("terms_sec1", "1. Izinhlinzeko Jikelele");
        zu.put("terms_sec2", "2. Imigomo Yamakhasimende");
        zu.put("terms_sec3", "3. Ithimba Lebhizinisi");
        zu.put("terms_sec4", "4. Imigomo Kamasipala & Eskom");
        zu.put("terms_sec5", "5. Ezomthetho Nokuhambisana");
        
        zu.put("payment_successful", "Inkokhelo Iphumelele");
        zu.put("purchase_successful", "Ukuthenga kwakho kuphumelele");
        zu.put("your_token", "Ithokheni Yakho");
        zu.put("transaction_id", "I-ID Yentengo");
        zu.put("copy_id", "Kopisha I-ID");
        zu.put("back_home", "Buyela Ekhaya");
        zu.put("view_history", "Bona Umlando");
        zu.put("thank_you", "Siyabonga ngokusebenzisa izinsizakalo zethu");
        DICTIONARY.put("isiZulu", zu);

        // ---------------------------------------------------------
        // 4. ISIXHOSA
        // ---------------------------------------------------------
        Map<String, String> xh = new HashMap<>();
        xh.put("settings", "Izicwangciso");
        xh.put("account", "I-akhawunti");
        xh.put("profile_settings", "Izicwangciso Zeprofayile");
        xh.put("terms_conditions", "Imiqathango Nemigaqo");
        xh.put("notifications", "Izaziso");
        xh.put("notif_pref", "Ukhetho Lwezaziso");
        xh.put("water_alerts", "Izilumkiso Zamanzi");
        xh.put("elec_alerts", "Izilumkiso Zombane");
        xh.put("app_pref", "Ukhetho Losetyenziso");
        xh.put("language", "Ulwimi");
        xh.put("help_support", "Uncedo Nenkxaso");
        xh.put("about", "Malunga ne-AquaVolt");
        xh.put("logout", "Phuma");
        xh.put("confirm_logout", "Uqinisekile ukuba ufuna ukuphuma?");
        xh.put("water", "Amanzi");
        xh.put("electricity", "Umbane");
        xh.put("water_hist", "Imbali Yokusetyenziswa Kwamanzi");
        xh.put("elec_hist", "Imbali Yokusetyenziswa Kombane");
        xh.put("pay_water", "Hlawula Amatyala Amanzi");
        xh.put("buy_elec", "Thenga Umbane");
        xh.put("water_hist_btn", "Imbali yamanzi");
        xh.put("elec_hist_btn", "Imbali yombane");
        xh.put("exit", "Phuma");
        xh.put("alerts", "Izilumkiso");
        xh.put("username", "Igama lomsebenzisi");
        xh.put("meter_number", "Inombolo Yemitha");
        xh.put("address", "Idilesi");
        xh.put("select_amount", "Khetha Isixa");
        xh.put("custom", "ESAKHO");
        xh.put("continue", "QHUBEKA");
        xh.put("back", "EMVA");
        xh.put("enter_meter", "Ngenisa Inombolo Yakho Yemitha");
        xh.put("meter_empty", "Indawo yenombolo yemitha ayinanto");
        xh.put("meter_digits", "Inombolo yemitha kufuneka ibe namanani kuphela!");
        xh.put("enter_address", "Ngenisa Idilesi Yakho");
        xh.put("address_empty", "Indawo yedilesi ayinanto");
        xh.put("choose_amount", "Khetha okanye ngenisa Isixa Sakho!");
        xh.put("select_one", "Khetha Iqhosha elinye");
        xh.put("enter_amount", "Ngenisa isixa sakho");
        xh.put("amount_empty", "Isixa asikwazi ukungena nto");
        xh.put("min_purchase", "Ubuncinane bokuthenga yi-R50");
        xh.put("amount_less", "Isixa asikwazi ukuba ngaphantsi kwe-R50");
        xh.put("amount_digits", "Isixa kufuneka sibe namanani kuphela");
        
        xh.put("terms_title", "Imiqathango Nemigaqo");
        xh.put("terms_sec1", "1. Imiqathango Jikelele");
        xh.put("terms_sec2", "2. Imiqathango Yabathengi");
        xh.put("terms_sec3", "3. Iqela Lamashishini");
        xh.put("terms_sec4", "4. Imiqathango Kamasipala & Eskom");
        xh.put("terms_sec5", "5. Ezomthetho Nokuthobela");
        
        xh.put("payment_successful", "Intlawulo Iphumelele");
        xh.put("purchase_successful", "Ukuthenga kwakho kuphumelele");
        xh.put("your_token", "Itokheni Yakho");
        xh.put("transaction_id", "I-ID Yentlawulo");
        xh.put("copy_id", "Khuphela I-ID");
        xh.put("back_home", "Buyela Ekhaya");
        xh.put("view_history", "Jonga Imbali");
        xh.put("thank_you", "Enkosi ngokusebenzisa iinkonzo zethu");
        DICTIONARY.put("isiXhosa", xh);

        // ---------------------------------------------------------
        // 5. SESOTHO
        // ---------------------------------------------------------
        Map<String, String> st = new HashMap<>();
        st.put("settings", "Litlhophiso");
        st.put("account", "Akhaonto");
        st.put("profile_settings", "Litlhophiso tsa Profaele");
        st.put("terms_conditions", "Melao le Maemo");
        st.put("notifications", "Tsebiso");
        st.put("notif_pref", "Khetho ea Tsebiso");
        st.put("water_alerts", "Litemoso tsa Metsi");
        st.put("elec_alerts", "Litemoso tsa Motlakase");
        st.put("app_pref", "Khetho ea Sesebelisoa");
        st.put("language", "Puo");
        st.put("help_support", "Thuso le Tshehetso");
        st.put("about", "Mabapi le AquaVolt");
        st.put("logout", "Tsoa");
        st.put("confirm_logout", "Na u na le bonnete ba hore u batla ho tsoa?");
        st.put("water", "Metsi");
        st.put("electricity", "Motlakase");
        st.put("water_hist", "Nalane ea Tšebeliso ea Metsi");
        st.put("elec_hist", "Nalane ea Tšebeliso ea Motlakase");
        st.put("pay_water", "Lefa Likoloto tsa Metsi");
        st.put("buy_elec", "Reka Motlakase");
        st.put("water_hist_btn", "Nalane ea metsi");
        st.put("elec_hist_btn", "Nalane ea motlakase");
        st.put("exit", "Tsoa");
        st.put("alerts", "Litemoso");
        st.put("username", "Lebitso la mosebedisi");
        st.put("meter_number", "Nomoro ea Metara");
        st.put("address", "Aterese");
        st.put("select_amount", "Khetha Chelete");
        st.put("custom", "EA HAO");
        st.put("continue", "TSOELA PELE");
        st.put("back", "MORA");
        st.put("enter_meter", "Kenya Nomoro ea Hao ea Metara");
        st.put("meter_empty", "Sebaka sa nomoro ea metara se se na letho");
        st.put("meter_digits", "Nomoro ea metara e lokela ho ba le linomoro feela!");
        st.put("enter_address", "Kenya Aterese ea Hao");
        st.put("address_empty", "Sebaka sa aterese se se na letho");
        st.put("choose_amount", "Khetha kapa u kenye Chelete ea Hao!");
        st.put("select_one", "Khetha Konopo e le 'ngoe");
        st.put("enter_amount", "Kenya chelete ea hau");
        st.put("amount_empty", "Chelete e ke ke ea ba letho");
        st.put("min_purchase", "Theko e tlase ke R50");
        st.put("amount_less", "Chelete e ke ke ea ba tlase ho R50");
        st.put("amount_digits", "Chelete e lokela ho ba le linomoro feela");
        
        st.put("terms_title", "Melao le Maemo");
        st.put("terms_sec1", "1. Melao e Akaretsang");
        st.put("terms_sec2", "2. Melao ea Bareki");
        st.put("terms_sec3", "3. Sehlopha sa Khoebo");
        st.put("terms_sec4", "4. Melao ea Masepala & Eskom");
        st.put("terms_sec5", "5. Tsa Molao le Boitlhompho");
        
        st.put("payment_successful", "Tefo e Atlehile");
        st.put("purchase_successful", "Theko ea hau e atlehile");
        st.put("your_token", "Tokhene ea Hao");
        st.put("transaction_id", "ID ea Transaction");
        st.put("copy_id", "Kopitsa ID");
        st.put("back_home", "Khutlela Hae");
        st.put("view_history", "Bona Nalane");
        st.put("thank_you", "Kea leboha ka ho sebelisa litšebeletso tsa rona");
        DICTIONARY.put("SeSotho", st);

        // ---------------------------------------------------------
        // 6. SEPEDI
        // ---------------------------------------------------------
        Map<String, String> nso = new HashMap<>();
        nso.put("settings", "Ditlhophišo");
        nso.put("account", "Akhaonte");
        nso.put("profile_settings", "Ditlhophišo tša Profaele");
        nso.put("terms_conditions", "Melao le Maemo");
        nso.put("notifications", "Tsebišo");
        nso.put("notif_pref", "Khetho ya Tsebišo");
        nso.put("water_alerts", "Ditemošo tša Meetse");
        nso.put("elec_alerts", "Ditemošo tša Mohlagase");
        nso.put("app_pref", "Khetho ya App");
        nso.put("language", "Leleme");
        nso.put("help_support", "Thušo le Thekgo");
        nso.put("about", "Mabapi le AquaVolt");
        nso.put("logout", "Tswa");
        nso.put("confirm_logout", "Na o na le bonnete bja gore o nyaka go tswa?");
        nso.put("water", "Meetse");
        nso.put("electricity", "Mohlagase");
        nso.put("water_hist", "Histori ya Tšhomišo ya Meetse");
        nso.put("elec_hist", "Histori ya Tšhomišo ya Mohlagase");
        nso.put("pay_water", "Lefa Dikoloto tša Meetse");
        nso.put("buy_elec", "Reka Mohlagase");
        nso.put("water_hist_btn", "Histori ya meetse");
        nso.put("elec_hist_btn", "Histori ya mohlagase");
        nso.put("exit", "Tswa");
        nso.put("alerts", "Ditemošo");
        nso.put("username", "Leina la mošomiši");
        nso.put("meter_number", "Nomoro ya Metara");
        nso.put("address", "Aterese");
        nso.put("select_amount", "Kgetha Tšhelete");
        nso.put("custom", "YA GAGO");
        nso.put("continue", "TŠWELA PELE");
        nso.put("back", "MORA");
        nso.put("enter_meter", "Tsenya Nomoro ya Gago ya Metara");
        nso.put("meter_empty", "Lefelo la nomoro ya metara le se na selo");
        nso.put("meter_digits", "Nomoro ya metara e swanetše go ba le dipalo fela!");
        nso.put("enter_address", "Tsenya Aterese ya Gago");
        nso.put("address_empty", "Lefelo la aterese le se na selo");
        nso.put("choose_amount", "Kgetha goba o tsenye Tšhelete ya Gago!");
        nso.put("select_one", "Kgetha Konopi e tee");
        nso.put("enter_amount", "Tsenya tšhelete ya gago");
        nso.put("amount_empty", "Tšhelete e ka se be le selo");
        nso.put("min_purchase", "Theko ya fase ke R50");
        nso.put("amount_less", "Tšhelete e ka se be ka fase ga R50");
        nso.put("amount_digits", "Tšhelete e swanetše go ba le dipalo fela");
        
        nso.put("terms_title", "Melao le Maemo");
        nso.put("terms_sec1", "1. Melao ya Kakaretšo");
        nso.put("terms_sec2", "2. Melao ya Bareki");
        nso.put("terms_sec3", "3. Sehlopha sa Kgwebo");
        nso.put("terms_sec4", "4. Melao ya Mmasepala & Eskom");
        nso.put("terms_sec5", "5. Tša Molao le Boikobetšo");
        
        nso.put("payment_successful", "Tefo e Atlegile");
        nso.put("purchase_successful", "Theko ya gago e atlegile");
        nso.put("your_token", "Thekene ya Gago");
        nso.put("transaction_id", "ID ya Transakšene");
        nso.put("copy_id", "Kopiša ID");
        nso.put("back_home", "Boela Gae");
        nso.put("view_history", "Bona Histori");
        nso.put("thank_you", "Re leboga go šomiša ditirelo tša rena");
        DICTIONARY.put("Sepedi", nso);

        // ---------------------------------------------------------
        // 7. SETSWANA
        // ---------------------------------------------------------
        Map<String, String> tn = new HashMap<>();
        tn.put("settings", "Ditlhophiso");
        tn.put("account", "Akhaonto");
        tn.put("profile_settings", "Ditlhophiso tsa Porofaele");
        tn.put("terms_conditions", "Melawana le Maemo");
        tn.put("notifications", "Kitso");
        tn.put("notif_pref", "Khetho ya Kitso");
        tn.put("water_alerts", "Ditemoso tsa Metsi");
        tn.put("elec_alerts", "Ditemoso tsa Motlakase");
        tn.put("app_pref", "Khetho ya App");
        tn.put("language", "Puo");
        tn.put("help_support", "Thuso le Tshegetso");
        tn.put("about", "Ka ga AquaVolt");
        tn.put("logout", "Tswa");
        tn.put("confirm_logout", "A o na le bonnete ba gore o batla go tswa?");
        tn.put("water", "Metsi");
        tn.put("electricity", "Motlakase");
        tn.put("water_hist", "Hisetori ya Tiriso ya Metsi");
        tn.put("elec_hist", "Hisetori ya Tiriso ya Motlakase");
        tn.put("pay_water", "Duela Dikoloto tsa Metsi");
        tn.put("buy_elec", "Reka Motlakase");
        tn.put("water_hist_btn", "Hisetori ya metsi");
        tn.put("elec_hist_btn", "Hisetori ya motlakase");
        tn.put("exit", "Tswa");
        tn.put("alerts", "Ditemoso");
        tn.put("username", "Leina la modirisi");
        tn.put("meter_number", "Nomoro ya Metara");
        tn.put("address", "Aterese");
        tn.put("select_amount", "Tlhopha Madi");
        tn.put("custom", "YA GAGO");
        tn.put("continue", "TSWELELA PELE");
        tn.put("back", "MORAGO");
        tn.put("enter_meter", "Tsenya Nomoro ya Gago ya Metara");
        tn.put("meter_empty", "Lefelo la nomoro ya metara ga le na sepe");
        tn.put("meter_digits", "Nomoro ya metara e tshwanetse go nna le dinomoro fela!");
        tn.put("enter_address", "Tsenya Aterese ya Gago");
        tn.put("address_empty", "Lefelo la aterese ga le na sepe");
        tn.put("choose_amount", "Tlhopha kgotsa o tsenye Madi a Gago!");
        tn.put("select_one", "Tlhopha Konopo e le nngwe");
        tn.put("enter_amount", "Tsenya madi a gago");
        tn.put("amount_empty", "Madi ga a kgone go nna le sepe");
        tn.put("min_purchase", "Theko e nnye ke R50");
        tn.put("amount_less", "Madi ga a kgone go nna kwa tlase ga R50");
        tn.put("amount_digits", "Madi a tshwanetse go nna le dinomoro fela");
        
        tn.put("terms_title", "Melawana le Maemo");
        tn.put("terms_sec1", "1. Melawana e e Akaretsang");
        tn.put("terms_sec2", "2. Melawana ya Batlhapisi");
        tn.put("terms_sec3", "3. Setlhopha sa Kgwebo");
        tn.put("terms_sec4", "4. Melawana ya Masepala & Eskom");
        tn.put("terms_sec5", "5. Tsa Molao le Boikanyego");
        
        tn.put("payment_successful", "Tuelo e Atlegile");
        tn.put("purchase_successful", "Theko ya gago e atlegile");
        tn.put("your_token", "Thekene ya Gago");
        tn.put("transaction_id", "ID ya Transakesene");
        tn.put("copy_id", "Kopisa ID");
        tn.put("back_home", "Boela Gae");
        tn.put("view_history", "Bona Hisetori");
        tn.put("thank_you", "Re a leboga ka go dirisa ditirelo tsa rona");
        DICTIONARY.put("Setswana", tn);

        // ---------------------------------------------------------
        // 8. XITSONGA (itsonga)
        // ---------------------------------------------------------
        Map<String, String> ts = new HashMap<>();
        ts.put("settings", "Milawu");
        ts.put("account", "Akhawunti");
        ts.put("profile_settings", "Milawu ya Purofayili");
        ts.put("terms_conditions", "Milawu na Switandzhaku");
        ts.put("notifications", "Switiviso");
        ts.put("notif_pref", "Ntivo lowu Tsakelaka");
        ts.put("water_alerts", "Swikombiso swa Mati");
        ts.put("elec_alerts", "Swikombiso swa Gezi");
        ts.put("app_pref", "Ntivo lowu Tsakelaka wa App");
        ts.put("language", "Ririmi");
        ts.put("help_support", "Mpfuno na Nseketelo");
        ts.put("about", "Hi AquaVolt");
        ts.put("logout", "Huma");
        ts.put("confirm_logout", "Xana u tiyisekile leswaku u lava ku huma?");
        ts.put("water", "Mati");
        ts.put("electricity", "Gezi");
        ts.put("water_hist", "Matimu ya Matirhiselo ya Mati");
        ts.put("elec_hist", "Matimu ya Matirhiselo ya Gezi");
        ts.put("pay_water", "Hakela Xikweleti xa Mati");
        ts.put("buy_elec", "Xava Gezi");
        ts.put("water_hist_btn", "Matimu ya mati");
        ts.put("elec_hist_btn", "Matimu ya gezi");
        ts.put("exit", "Huma");
        ts.put("alerts", "Swikombiso");
        ts.put("username", "Vito ra mutirhisi");
        ts.put("meter_number", "Nomboro ya Mitara");
        ts.put("address", "Adirese");
        ts.put("select_amount", "Hlawula Mali");
        ts.put("custom", "YA WENA");
        ts.put("continue", "YA EMBENI");
        ts.put("back", "ENDO");
        ts.put("enter_meter", "Nghenisa Nomboro ya Wena ya Mitara");
        ts.put("meter_empty", "Ndhawu ya nomboro ya mitara yi ri hava nchumu");
        ts.put("meter_digits", "Nomboro ya mitara yi fanele yi va ni tinomboro ntsena!");
        ts.put("enter_address", "Nghenisa Adirese ya Wena");
        ts.put("address_empty", "Ndhawu ya adirese yi ri hava nchumu");
        ts.put("choose_amount", "Hlawula kumbe u nghenisa Mali ya Wena!");
        ts.put("select_one", "Hlawula Boto yin'we");
        ts.put("enter_amount", "Nghenisa mali ya wena");
        ts.put("amount_empty", "Mali a yi nge vi hava nchumu");
        ts.put("min_purchase", "Ku xava loko ku ri hansi i R50");
        ts.put("amount_less", "Mali a yi nge vi ehansi ka R50");
        ts.put("amount_digits", "Mali yi fanele yi va ni tinomboro ntsena");
        
        ts.put("terms_title", "Milawu na Switandzhaku");
        ts.put("terms_sec1", "1. Milawu ya Manguva");
        ts.put("terms_sec2", "2. Milawu ya Vaxavi");
        ts.put("terms_sec3", "3. Ntlawa wa Bindzu");
        ts.put("terms_sec4", "4. Milawu ya Masipala & Eskom");
        ts.put("terms_sec5", "5. Ya Nawu na Ku Landzelerisa");
        
        ts.put("payment_successful", "Nhakelo Yi Humelerile");
        ts.put("purchase_successful", "Ku xava ka wena ku humelerile");
        ts.put("your_token", "Thekene ya Wena");
        ts.put("transaction_id", "ID ya Nxavo");
        ts.put("copy_id", "Kopa ID");
        ts.put("back_home", "Tlhelela Ekaya");
        ts.put("view_history", "Vona Matimu");
        ts.put("thank_you", "Ha khensa ku tirhisa vukorhokeri bya hina");
        DICTIONARY.put("itsonga", ts);

        // ---------------------------------------------------------
        // 9. TSHIVENDA
        // ---------------------------------------------------------
        Map<String, String> ve = new HashMap<>();
        ve.put("settings", "Zwidodombedzwa");
        ve.put("account", "Akhaunthu");
        ve.put("profile_settings", "Zwidodombedzwa zwa Profaili");
        ve.put("terms_conditions", "Milayo na Themendelo");
        ve.put("notifications", "Ndivhadzo");
        ve.put("notif_pref", "Khethekanyo ya Ndivhadzo");
        ve.put("water_alerts", "Tsumbo dza Madi");
        ve.put("elec_alerts", "Tsumbo dza Mudagasi");
        ve.put("app_pref", "Khethekanyo ya App");
        ve.put("language", "Luambo");
        ve.put("help_support", "Thivhulungi na Tshitshavha");
        ve.put("about", "Nga ha AquaVolt");
        ve.put("logout", "Bva");
        ve.put("confirm_logout", "Ndi vha na vhukhakhi ha uri ni toda u bva?");
        ve.put("water", "Madi");
        ve.put("electricity", "Mudagasi");
        ve.put("water_hist", "Ndodzandevhe ya Kushumiswa ha Madi");
        ve.put("elec_hist", "Ndodzandevhe ya Kushumiswa ha Mudagasi");
        ve.put("pay_water", "Badela Mikwele ya Madi");
        ve.put("buy_elec", "Renga Mudagasi");
        ve.put("water_hist_btn", "Ndodzandevhe ya madi");
        ve.put("elec_hist_btn", "Ndodzandevhe ya mudagasi");
        ve.put("exit", "Bva");
        ve.put("alerts", "Tsumbo");
        ve.put("username", "Dzina la mushumisi");
        ve.put("meter_number", "Nomoro ya Mitara");
        ve.put("address", "Adiresi");
        ve.put("select_amount", "Nanga Mulaedza");
        ve.put("custom", "YA VHONE");
        ve.put("continue", "BVELELA PHANDA");
        ve.put("back", "MURAHO");
        ve.put("enter_meter", "Dzhenisa Nomoro ya Vhone ya Mitara");
        ve.put("meter_empty", "Fhethu ha nomoro ya mitara a huna tshithu");
        ve.put("meter_digits", "Nomoro ya mitara i fanela u vha na dzinomoro fhedzi!");
        ve.put("enter_address", "Dzhenisa Adiresi ya Vhone");
        ve.put("address_empty", "Fhethu ha adiresi a huna tshithu");
        ve.put("choose_amount", "Nanga kana u dzhenisa Mulaedza wa Vhone!");
        ve.put("select_one", "Nanga Butoni nthihi");
        ve.put("enter_amount", "Dzhenisa mulaedza wa vhone");
        ve.put("amount_empty", "Mulaedza a u nga vhi na tshithu");
        ve.put("min_purchase", "U renga ho fhasi ndi R50");
        ve.put("amount_less", "Mulaedza a u nga vhi fhasi ha R50");
        ve.put("amount_digits", "Mulaedza u fanela u vha na dzinomoro fhedzi");
        
        ve.put("terms_title", "Milayo na Themendelo");
        ve.put("terms_sec1", "1. Milayo ya Tshijikile");
        ve.put("terms_sec2", "2. Milayo ya Vharengi");
        ve.put("terms_sec3", "3. Tshigwada tsha Vhubindudzi");
        ve.put("terms_sec4", "4. Milayo ya Masepala & Eskom");
        ve.put("terms_sec5", "5. Ya Mulayo na U Tevhedza");
        
        ve.put("payment_successful", "Tshiholelwa Tsho Bvelela");
        ve.put("purchase_successful", "U renga ha vhone ho bvelela");
        ve.put("your_token", "Thekhene ya Vhone");
        ve.put("transaction_id", "ID ya Tshifhinga");
        ve.put("copy_id", "Khopi ID");
        ve.put("back_home", "Humela Hayani");
        ve.put("view_history", "Vhona Ndodzandevhe");
        ve.put("thank_you", "Ri a livhuwa nga u shumisa tshumelo dzashu");
        DICTIONARY.put("Tshivenda", ve);
    }

    /**
     * Gets the translated string for a specific key in the user's saved language.
     */
    public static String get(String key) {
        String lang = "English";
        if (Session.customer != null) {
            lang = SettingsStore.get(Session.username(), "language", "English");
        }

        Map<String, String> dict = DICTIONARY.get(lang);
        if (dict == null) dict = DICTIONARY.get("English"); // fallback

        String translated = dict.get(key);
        if (translated == null) {
            translated = DICTIONARY.get("English").get(key); // fallback to english if key missing
        }
        return translated != null ? translated : key;
    }
}