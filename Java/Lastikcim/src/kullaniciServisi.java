import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class kullaniciServisi {

    private Connection connection;

    public kullaniciServisi(Connection connection) {
        this.connection = connection;
    }

    // musteri randevusu (KONTROLLÜ)
    public void randevuOlustur(String username, Date tarih, String saat, String adSoyad, String plaka, String arac, String genislik, String yukseklik, String jant) throws SQLException {
        // 1. Geçmiş tarih & saat kontrolü
        java.util.Calendar now = java.util.Calendar.getInstance();
        java.util.Calendar selected = java.util.Calendar.getInstance();
        selected.setTime(tarih);
        String[] saatSplit = saat.split(":");
        selected.set(java.util.Calendar.HOUR_OF_DAY, Integer.parseInt(saatSplit[0]));
        selected.set(java.util.Calendar.MINUTE, Integer.parseInt(saatSplit[1]));
        selected.set(java.util.Calendar.SECOND, 0);
        selected.set(java.util.Calendar.MILLISECOND, 0);

        if (selected.before(now)) {
            throw new SQLException("Geçmiş bir tarihe veya saate randevu alamazsınız!");
        }

        // 2. Aynı tarih ve saatte randevu var mı?
        String kontrolQuery = "SELECT COUNT(*) FROM randevular WHERE tarih = ? AND saat = ?";
        try (PreparedStatement kontrolStmt = connection.prepareStatement(kontrolQuery)) {
            kontrolStmt.setDate(1, tarih);
            kontrolStmt.setString(2, saat);
            ResultSet rs = kontrolStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                throw new SQLException("Bu tarih ve saatte zaten bir randevu var!");
            }
        }

        String query = "INSERT INTO randevular (username, tarih, saat, adSoyad, plaka, markaModel, genislik, yukseklik, jant, isletmeUsername, durum) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, username);
        stmt.setDate(2, tarih);
        stmt.setString(3, saat);
        stmt.setString(4, adSoyad);
        stmt.setString(5, plaka);
        stmt.setString(6, arac);
        stmt.setString(7, genislik);
        stmt.setString(8, yukseklik);
        stmt.setString(9, jant);
        stmt.setString(10, "isletme1");
        stmt.setString(11, "Bekliyor"); // Yeni randevu başlangıç durumu
        stmt.executeUpdate();
    }

    // musteri randevusunu tabloya getirme
public List<Randevu> getMusteriRandevulari(String username) throws SQLException {
    List<Randevu> randevular = new ArrayList<>();
    // Şu anki tarih ve saat (bugünün saatinden öncekiler gelmesin diye)
    java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm");
    String nowTime = sdf.format(new java.util.Date());

    String query = "SELECT adSoyad, tarih, saat, plaka, markaModel, durum " +
            "FROM randevular WHERE username = ? AND " +
            "((tarih > ?) OR (tarih = ? AND saat >= ?)) " +
            "ORDER BY tarih, saat";

    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setString(1, username);
        stmt.setDate(2, today);
        stmt.setDate(3, today);
        stmt.setString(4, nowTime);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Randevu randevu = new Randevu();
            randevu.setAdSoyad(rs.getString("adSoyad"));
            randevu.setTarih(rs.getDate("tarih"));
            randevu.setSaat(rs.getString("saat"));
            randevu.setPlaka(rs.getString("plaka"));
            randevu.setAracBilgileri(rs.getString("markaModel"));
            randevu.setDurum(rs.getString("durum"));
            randevular.add(randevu);
        }
    }

    return randevular;
}

    public static class Randevu {

        private String adSoyad;
        private Date tarih;
        private String saat;
        private String plaka;
        private String aracBilgileri;
        private String genislik;
        private String yukseklik;
        private String jant;
        private String durum;

        // Getters ve Setters...
        public String getAdSoyad() { return adSoyad; }
        public void setAdSoyad(String adSoyad) { this.adSoyad = adSoyad; }
        public Date getTarih() { return tarih; }
        public void setTarih(Date tarih) { this.tarih = tarih; }
        public String getSaat() { return saat; }
        public void setSaat(String saat) { this.saat = saat; }
        public String getPlaka() { return plaka; }
        public void setPlaka(String plaka) { this.plaka = plaka; }
        public String getAracBilgileri() { return aracBilgileri; }
        public void setAracBilgileri(String aracBilgileri) { this.aracBilgileri = aracBilgileri; }
        public String getGenislik() { return genislik; }
        public void setGenislik(String genislik) { this.genislik = genislik; }
        public String getYukseklik() { return yukseklik; }
        public void setYukseklik(String yukseklik) { this.yukseklik = yukseklik; }
        public String getJant() { return jant; }
        public void setJant(String jant) { this.jant = jant; }
        public String getDurum() { return durum; }
        public void setDurum(String durum) { this.durum = durum; }
    }

    // randevu iptal butonu icin
    public void randevuIptal(String username, Date tarih, String saat) throws SQLException {
        String query = "DELETE FROM randevular WHERE username = ? AND tarih = ? AND saat = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, username);
        stmt.setDate(2, tarih);
        stmt.setString(3, saat);
        stmt.executeUpdate();
    }

    // İşletmenin randevu tablosu için
    public List<Randevu> getIsletmeRandevulari(String isletmeUsername) throws SQLException {
        List<Randevu> randevular = new ArrayList<>();
        String query = "SELECT tarih, saat, adSoyad, plaka, markaModel, genislik, yukseklik, jant, durum "
                + "FROM randevular WHERE isletmeUsername = ? ORDER BY tarih, saat";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, isletmeUsername);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Randevu randevu = new Randevu();
                randevu.setTarih(rs.getDate("tarih"));
                randevu.setSaat(rs.getString("saat"));
                randevu.setAdSoyad(rs.getString("adSoyad"));
                randevu.setPlaka(rs.getString("plaka"));
                randevu.setAracBilgileri(rs.getString("markaModel"));
                randevu.setGenislik(rs.getString("genislik"));
                randevu.setYukseklik(rs.getString("yukseklik"));
                randevu.setJant(rs.getString("jant"));
                randevu.setDurum(rs.getString("durum"));
                randevular.add(randevu);
            }
        }

        return randevular;
    }
}