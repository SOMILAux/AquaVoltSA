//Final update done by Oyama
package za.ac.cput.aquavoltsa.GUI;

import za.ac.cput.aquavoltsa.DAO.DAO;

/**
 * It remembers that it did this in the table APP_META, so rows you delete in NetBeans stay deleted.
 */
final class DemoData {
    private DemoData() { }

    static void seed() {
        if (DAO.meta("demo_seeded") != null) return;
        if (DAO.count("customers") == 0) {   // a brand-new database; an existing one is left exactly as it is
            demo("Cheese Mokoena", "9001015800086", "Cheese", "Cheese@123", "cheese@mail.co.za", "0821234567",
                    "12 Long Street, Cape Town", "MTR-44821", "SN-77120", "WTR-1024", "ACC-9981", UserStore.VERIFIED);
            demo("Thando Nkosi", "9505055800083", "Thando", "Thando@123", "thando@gmail.com", "0735550142",
                    "8 Vilakazi Street, Soweto", "MTR-10977", "SN-30551", "", "", UserStore.PENDING);
            demo("Naledi Dlamini", "0001015800087", "Naledi", "Naledi@123", "naledi@outlook.com", "0614449087",
                    "45 Berea Road, Durban", "", "", "WTR-5521", "ACC-1207", UserStore.PENDING);
            demo("Sipho Khumalo", "8812125800085", "Sipho", "Sipho@123", "sipho.k@mail.com", "0794447712",
                    "3 Church Street, Pretoria", "MTR-88231", "SN-12089", "WTR-7710", "ACC-3345", UserStore.VERIFIED);

            NotificationStore.send("Cheese", "WATER", "Drought Alert", "Water restrictions are in place in your area. Please use water sparingly.", "system");
            NotificationStore.send("Cheese", "ELECTRICITY", "Loadshedding Update", "Stage 2 loadshedding today from 18:00 to 20:30.", "system");
            NotificationStore.send("Cheese", "WATER", "Usage Warning", "Your water usage is 20% above your daily average.", "system");
            NotificationStore.send("Cheese", "PAYMENT", "Payment Reminder", "Your electricity account payment is due in 3 days.", "system");
            NotificationStore.send("Cheese", "SYSTEM", "System Update", "AquaVolt SA now has notifications and payment history.", "system");

            TransactionStore.record("Cheese", "WTR-2026-10-20-09-15-02", "WATER", "1024", 250, 2.5, "Debit / Credit Card", "Successful", null);
            TransactionStore.record("Cheese", "ELE-2026-10-21-14-02-41", "ELECTRICITY", "44821", 100, 2.5, "EFT", "Successful", "4821 7733 1290 6654");
            TransactionStore.record("Sipho", "ELE-2026-10-22-18-30-10", "ELECTRICITY", "88231", 150, 2.5, "Debit / Credit Card", "Successful", "9034 1187 4520 7781");
        }
        DAO.setMeta("demo_seeded", "Y");
    }

    private static void demo(String name, String idNo, String user, String pass, String mail, String phone,
                             String addr, String meter, String serial, String water, String acc, String status) {
        UserStore.User u = new UserStore.User();
        u.fullName = name; u.idNumber = idNo; u.username = user; u.passHash = UserStore.hash(pass);
        u.email = mail; u.phone = phone; u.address = addr; u.meter = meter; u.serial = serial;
        u.waterMeter = water; u.accountNo = acc; u.status = status;
        UserStore.add(u);
    }
}