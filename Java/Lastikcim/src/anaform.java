
import java.awt.CardLayout;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class anaform extends javax.swing.JFrame {

    private String currentUserName;
    private stokIslemleri stokIslem;
    private MuhasebeIslemleri muhasebeIslem;
    private RandevuIslemleri randevuIslem;

    /**
     * Creates new form anaform
     */
    public anaform(String username) {
        this.currentUserName = username;
        initComponents();

        CardLayout cl = (CardLayout) (icerikPanel.getLayout());
        cl.addLayoutComponent(anasayfaPanel, "anaSayfaPanel");
        cl.addLayoutComponent(muhasebePanel, "muhasebePanel");
        cl.addLayoutComponent(stokPanel, "stokPanel");
        cl.addLayoutComponent(randevuPanel1, "randevuPanel");

        jLabel1.setText("Kullanıcı: " + this.currentUserName);

        // ilk açılış: anasyfa
        cl.show(icerikPanel, "anaSayfaPanel");

        //tabloyu yeniler
        stokIslem = new stokIslemleri(this, stokTablo);
        stokIslem.stokTabloYenile();

        muhasebeIslem = new MuhasebeIslemleri(this, muhasebeTablo, muhasebeTarihSecici);
        muhasebeIslem.tabloyuBuguneGoreYenile();

        randevuIslem = new RandevuIslemleri(this, randevuTablo, "isletme1");
        randevuIslem.randevuTabloYenile();

        //anasayfa kazanç
        double gelir = muhasebeIslem.getBugunkuToplamGelir();
        anasayfaGunluk.setText(String.format("%.2f", gelir) + " TL");

        //anasayfa randevu
        anasayfaRandevuTabloYenile();
        
        //tabloyu sırala
        muhasebeTablo.setAutoCreateRowSorter(true);

    }

    public void anasayfaRandevuTabloYenile() {
        List<Object[]> randevular = randevuIslem.getYaklasanRandevular(5); // İlk 5 yaklaşan randevu
        DefaultTableModel model = (DefaultTableModel) anasayfaTablo.getModel();
        model.setRowCount(0);
        for (Object[] row : randevular) {
            model.addRow(row);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        navigasyonPanel = new javax.swing.JPanel();
        anasayfaButon1 = new javax.swing.JButton();
        muhasebeButon = new javax.swing.JButton();
        stokButon = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        randevuButon1 = new javax.swing.JButton();
        icerikPanel = new javax.swing.JPanel();
        anasayfaPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        anasayfaGunluk = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        anasayfaTablo = new javax.swing.JTable();
        muhasebePanel = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        gunlukGelirEkleButon = new javax.swing.JButton();
        gunlukGiderEkleButon = new javax.swing.JButton();
        aylikTabloButon = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        muhasebeTablo = new javax.swing.JTable();
        muhasebeTarihSecici = new com.toedter.calendar.JDateChooser();
        muhasebeGunluk = new javax.swing.JButton();
        muhasebeSilButon = new javax.swing.JButton();
        toplamGunlukButon = new javax.swing.JButton();
        toplamAylikButon = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        stokPanel = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        urunEkle = new javax.swing.JButton();
        urunCikar = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        stokTablo = new javax.swing.JTable();
        urunMiktar = new javax.swing.JButton();
        randevuPanel1 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        randevuGirisButon = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        randevuTablo = new javax.swing.JTable();
        tarihSecici = new com.toedter.calendar.JDateChooser();
        islemYapButon = new javax.swing.JButton();
        randevuSilIsletmeButon = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        navigasyonPanel.setBackground(new java.awt.Color(255, 153, 51));

        anasayfaButon1.setText("Anasayfa");
        anasayfaButon1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                anasayfaButon1ActionPerformed(evt);
            }
        });

        muhasebeButon.setText("Gelir/Gider");
        muhasebeButon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                muhasebeButonActionPerformed(evt);
            }
        });

        stokButon.setText("Stok");
        stokButon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stokButonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Geist Light", 0, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Kullanıcı: ");

        randevuButon1.setText("Randevu");
        randevuButon1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                randevuButon1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout navigasyonPanelLayout = new javax.swing.GroupLayout(navigasyonPanel);
        navigasyonPanel.setLayout(navigasyonPanelLayout);
        navigasyonPanelLayout.setHorizontalGroup(
            navigasyonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navigasyonPanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(navigasyonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(navigasyonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(muhasebeButon, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(stokButon, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(anasayfaButon1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(randevuButon1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(123, Short.MAX_VALUE))
        );
        navigasyonPanelLayout.setVerticalGroup(
            navigasyonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navigasyonPanelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(175, 175, 175)
                .addComponent(anasayfaButon1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(muhasebeButon, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(stokButon, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(randevuButon1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(275, Short.MAX_VALUE))
        );

        jSplitPane1.setLeftComponent(navigasyonPanel);

        icerikPanel.setLayout(new java.awt.CardLayout());

        jLabel2.setFont(new java.awt.Font("Geist Light", 0, 18)); // NOI18N
        jLabel2.setText("Lastikçim Yönetim Sistemi");

        jLabel3.setFont(new java.awt.Font("Geist Light", 0, 14)); // NOI18N
        jLabel3.setText("Yaklaşan Randevular");

        jLabel4.setFont(new java.awt.Font("Geist Light", 0, 14)); // NOI18N
        jLabel4.setText("Günlük Kazanç");

        anasayfaGunluk.setText("0 TL");

        anasayfaTablo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tarih", "Saat", "Ad Soyad", "Plaka"
            }
        ));
        jScrollPane4.setViewportView(anasayfaTablo);

        javax.swing.GroupLayout anasayfaPanelLayout = new javax.swing.GroupLayout(anasayfaPanel);
        anasayfaPanel.setLayout(anasayfaPanelLayout);
        anasayfaPanelLayout.setHorizontalGroup(
            anasayfaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(anasayfaPanelLayout.createSequentialGroup()
                .addGroup(anasayfaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(anasayfaPanelLayout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addGroup(anasayfaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(anasayfaGunluk)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 585, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(anasayfaPanelLayout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(196, Short.MAX_VALUE))
        );
        anasayfaPanelLayout.setVerticalGroup(
            anasayfaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(anasayfaPanelLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(anasayfaGunluk)
                .addGap(44, 44, 44)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(183, Short.MAX_VALUE))
        );

        icerikPanel.add(anasayfaPanel, "card2");

        jLabel5.setFont(new java.awt.Font("Geist Light", 0, 18)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Gelir/Gider Bilançosu");
        jLabel5.setVerifyInputWhenFocusTarget(false);

        gunlukGelirEkleButon.setText("Ekle");
        gunlukGelirEkleButon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gunlukGelirEkleButonActionPerformed(evt);
            }
        });

        gunlukGiderEkleButon.setText("Ekle");
        gunlukGiderEkleButon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gunlukGiderEkleButonActionPerformed(evt);
            }
        });

        aylikTabloButon.setText("Aylık");
        aylikTabloButon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aylikTabloButonActionPerformed(evt);
            }
        });

        jLabel16.setText("Gelir Ekle");

        jLabel17.setText("Gider Ekle");

        muhasebeTablo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Tarih", "Tür", "Miktar", "İşi Yapan", "Açıklama"
            }
        ));
        jScrollPane2.setViewportView(muhasebeTablo);

        muhasebeGunluk.setText("Seç");
        muhasebeGunluk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                muhasebeGunlukActionPerformed(evt);
            }
        });

        muhasebeSilButon.setText("İşlemi Sil");
        muhasebeSilButon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                muhasebeSilButonActionPerformed(evt);
            }
        });

        toplamGunlukButon.setText("Günlük");
        toplamGunlukButon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toplamGunlukButonActionPerformed(evt);
            }
        });

        toplamAylikButon.setText("Aylık");
        toplamAylikButon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toplamAylikButonActionPerformed(evt);
            }
        });

        jLabel6.setText("Toplam Gelir Gider");

        javax.swing.GroupLayout muhasebePanelLayout = new javax.swing.GroupLayout(muhasebePanel);
        muhasebePanel.setLayout(muhasebePanelLayout);
        muhasebePanelLayout.setHorizontalGroup(
            muhasebePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(muhasebePanelLayout.createSequentialGroup()
                .addGroup(muhasebePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(muhasebePanelLayout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(muhasebePanelLayout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addGroup(muhasebePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(muhasebeSilButon)
                            .addGroup(muhasebePanelLayout.createSequentialGroup()
                                .addGroup(muhasebePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 594, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(muhasebePanelLayout.createSequentialGroup()
                                        .addGroup(muhasebePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, muhasebePanelLayout.createSequentialGroup()
                                                .addComponent(muhasebeTarihSecici, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(muhasebeGunluk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGroup(muhasebePanelLayout.createSequentialGroup()
                                                .addGroup(muhasebePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel16)
                                                    .addComponent(jLabel17))
                                                .addGap(93, 93, 93)
                                                .addGroup(muhasebePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(gunlukGiderEkleButon)
                                                    .addComponent(gunlukGelirEkleButon))))
                                        .addGroup(muhasebePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(muhasebePanelLayout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(aylikTabloButon, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(muhasebePanelLayout.createSequentialGroup()
                                                .addGap(159, 159, 159)
                                                .addGroup(muhasebePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel6)
                                                    .addGroup(muhasebePanelLayout.createSequentialGroup()
                                                        .addComponent(toplamGunlukButon)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(toplamAylikButon)))))))
                                .addGap(91, 91, 91)))))
                .addContainerGap(105, Short.MAX_VALUE))
        );
        muhasebePanelLayout.setVerticalGroup(
            muhasebePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(muhasebePanelLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71)
                .addGroup(muhasebePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(muhasebePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(gunlukGelirEkleButon)
                        .addComponent(jLabel16))
                    .addGroup(muhasebePanelLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(muhasebePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(toplamGunlukButon)
                            .addComponent(toplamAylikButon))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(muhasebePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(gunlukGiderEkleButon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addGroup(muhasebePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(muhasebeTarihSecici, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(muhasebePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(muhasebeGunluk)
                        .addComponent(aylikTabloButon)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(muhasebeSilButon)
                .addGap(85, 85, 85))
        );

        icerikPanel.add(muhasebePanel, "card3");

        jLabel40.setFont(new java.awt.Font("Geist Light", 0, 18)); // NOI18N
        jLabel40.setText("Stok Takibi");

        urunEkle.setText("Ürün Ekle");
        urunEkle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                urunEkleActionPerformed(evt);
            }
        });

        urunCikar.setText("Ürün Çıkar");
        urunCikar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                urunCikarActionPerformed(evt);
            }
        });

        stokTablo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Ürün İsmi", "Ürün Miktarı", "Ürün Fiyatı"
            }
        ));
        jScrollPane3.setViewportView(stokTablo);

        urunMiktar.setText("Ürün ve Fiyat Miktarı");
        urunMiktar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                urunMiktarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout stokPanelLayout = new javax.swing.GroupLayout(stokPanel);
        stokPanel.setLayout(stokPanelLayout);
        stokPanelLayout.setHorizontalGroup(
            stokPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(stokPanelLayout.createSequentialGroup()
                .addGroup(stokPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(stokPanelLayout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(stokPanelLayout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addGroup(stokPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 619, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(stokPanelLayout.createSequentialGroup()
                                .addComponent(urunEkle)
                                .addGap(18, 18, 18)
                                .addComponent(urunCikar)
                                .addGap(18, 18, 18)
                                .addComponent(urunMiktar)))))
                .addContainerGap(178, Short.MAX_VALUE))
        );
        stokPanelLayout.setVerticalGroup(
            stokPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, stokPanelLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(stokPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(urunEkle)
                    .addComponent(urunCikar)
                    .addComponent(urunMiktar))
                .addContainerGap(182, Short.MAX_VALUE))
        );

        icerikPanel.add(stokPanel, "card4");

        jLabel15.setFont(new java.awt.Font("Geist Light", 0, 18)); // NOI18N
        jLabel15.setText("Randevular");

        jLabel32.setText("Tarih Aralığı Seçin");

        randevuGirisButon.setText("Seç");
        randevuGirisButon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                randevuGirisButonActionPerformed(evt);
            }
        });

        randevuTablo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tarih", "Saat", "Ad Soyad", "Plaka", "Araç", "Lastik Genişlik", "Lastik Yükseklik", "Lastik Jant", "Durum"
            }
        ));
        jScrollPane1.setViewportView(randevuTablo);

        islemYapButon.setText("İşlem Yap");
        islemYapButon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                islemYapButonActionPerformed(evt);
            }
        });

        randevuSilIsletmeButon.setText("Randevuyu Sil");
        randevuSilIsletmeButon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                randevuSilIsletmeButonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout randevuPanel1Layout = new javax.swing.GroupLayout(randevuPanel1);
        randevuPanel1.setLayout(randevuPanel1Layout);
        randevuPanel1Layout.setHorizontalGroup(
            randevuPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(randevuPanel1Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(randevuPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(randevuPanel1Layout.createSequentialGroup()
                        .addComponent(randevuSilIsletmeButon, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(islemYapButon, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(randevuPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(randevuPanel1Layout.createSequentialGroup()
                            .addComponent(tarihSecici, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(29, 29, 29)
                            .addComponent(randevuGirisButon, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel32)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 695, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(94, Short.MAX_VALUE))
        );
        randevuPanel1Layout.setVerticalGroup(
            randevuPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(randevuPanel1Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64)
                .addComponent(jLabel32)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(randevuPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(randevuGirisButon, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(tarihSecici, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(53, 53, 53)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(randevuPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(islemYapButon)
                    .addComponent(randevuSilIsletmeButon))
                .addContainerGap(169, Short.MAX_VALUE))
        );

        icerikPanel.add(randevuPanel1, "card4");

        jSplitPane1.setRightComponent(icerikPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void anasayfaButon1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_anasayfaButon1ActionPerformed
        // TODO add your handling code here:

        CardLayout cl = (CardLayout) (icerikPanel.getLayout());
        cl.show(icerikPanel, "anaSayfaPanel");

        //geliri yeniler
        double gelir = muhasebeIslem.getBugunkuToplamGelir();
        anasayfaGunluk.setText(String.format("%.2f TL", gelir));

        // Yaklaşan randevu tablosunu günceller
        anasayfaRandevuTabloYenile();
    }//GEN-LAST:event_anasayfaButon1ActionPerformed

    private void muhasebeButonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_muhasebeButonActionPerformed
        // TODO add your handling code here:

        CardLayout cl = (CardLayout) (icerikPanel.getLayout());
        cl.show(icerikPanel, "muhasebePanel");

    }//GEN-LAST:event_muhasebeButonActionPerformed

    private void stokButonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stokButonActionPerformed
        // TODO add your handling code here:
        CardLayout cl = (CardLayout) (icerikPanel.getLayout());
        cl.show(icerikPanel, "stokPanel");
    }//GEN-LAST:event_stokButonActionPerformed

    private void randevuButon1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_randevuButon1ActionPerformed
        // TODO add your handling code here:
        CardLayout cl = (CardLayout) (icerikPanel.getLayout());
        cl.show(icerikPanel, "randevuPanel");
        randevuIslem.randevuTabloYenile();
    }//GEN-LAST:event_randevuButon1ActionPerformed

    private void urunEkleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_urunEkleActionPerformed
        // TODO add your handling code here:
        stokIslem.urunEkle();
    }//GEN-LAST:event_urunEkleActionPerformed

    private void randevuGirisButonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_randevuGirisButonActionPerformed
        // TODO add your handling code here:
        java.util.Date selectedDate = tarihSecici.getDate();
        if (selectedDate != null) {
            randevuIslem.randevuTabloTarihleYenile(new java.sql.Date(selectedDate.getTime()));
        }

    }//GEN-LAST:event_randevuGirisButonActionPerformed

    private void islemYapButonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_islemYapButonActionPerformed
        // TODO add your handling code here:
        randevuIslem.randevuIslemYap(this, muhasebeIslem);
    }//GEN-LAST:event_islemYapButonActionPerformed

    private void urunCikarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_urunCikarActionPerformed
        // TODO add your handling code here:
        stokIslem.urunCikar();

    }//GEN-LAST:event_urunCikarActionPerformed

    private void urunMiktarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_urunMiktarActionPerformed
        // TODO add your handling code here:
        stokIslem.urunMiktarGuncelle();
    }//GEN-LAST:event_urunMiktarActionPerformed

    private void muhasebeSilButonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_muhasebeSilButonActionPerformed
        // TODO add your handling code here:
        muhasebeIslem.kaydiSil();
    }//GEN-LAST:event_muhasebeSilButonActionPerformed

    private void muhasebeGunlukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_muhasebeGunlukActionPerformed
        // TODO add your handling code here:
        java.util.Date selectedDate = muhasebeTarihSecici.getDate();
        if (selectedDate != null) {
            muhasebeIslem.tabloyuTariheGoreYenile(new java.sql.Date(selectedDate.getTime()));
        }

    }//GEN-LAST:event_muhasebeGunlukActionPerformed

    private void aylikTabloButonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aylikTabloButonActionPerformed
        // TODO add your handling code here:
        muhasebeIslem.tabloyuAylikYenile();
    }//GEN-LAST:event_aylikTabloButonActionPerformed

    private void gunlukGiderEkleButonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gunlukGiderEkleButonActionPerformed
        // TODO add your handling code here:
        muhasebeIslem.gelirGiderEklePopup("gider");
    }//GEN-LAST:event_gunlukGiderEkleButonActionPerformed

    private void gunlukGelirEkleButonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gunlukGelirEkleButonActionPerformed
        // TODO add your handling code here:
        muhasebeIslem.gelirGiderEklePopup("gelir");
    }//GEN-LAST:event_gunlukGelirEkleButonActionPerformed

    private void randevuSilIsletmeButonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_randevuSilIsletmeButonActionPerformed
        // TODO add your handling code here:
        randevuIslem.isletmeRandevuSil();
    }//GEN-LAST:event_randevuSilIsletmeButonActionPerformed

    private void toplamGunlukButonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toplamGunlukButonActionPerformed
        // TODO add your handling code here:
        muhasebeIslem.gunlukToplamGoster();
    }//GEN-LAST:event_toplamGunlukButonActionPerformed

    private void toplamAylikButonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toplamAylikButonActionPerformed
        // TODO add your handling code here:
        muhasebeIslem.aylikToplamGoster();
    }//GEN-LAST:event_toplamAylikButonActionPerformed

    public static void main(String args[]) {

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(anaform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(anaform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(anaform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(anaform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                loginScreen login = new loginScreen(); //ilk olarak login ekranından başlatır
                login.setVisible(true);

            }

        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton anasayfaButon1;
    private javax.swing.JLabel anasayfaGunluk;
    private javax.swing.JPanel anasayfaPanel;
    private javax.swing.JTable anasayfaTablo;
    private javax.swing.JButton aylikTabloButon;
    private javax.swing.JButton gunlukGelirEkleButon;
    private javax.swing.JButton gunlukGiderEkleButon;
    private javax.swing.JPanel icerikPanel;
    private javax.swing.JButton islemYapButon;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JButton muhasebeButon;
    private javax.swing.JButton muhasebeGunluk;
    private javax.swing.JPanel muhasebePanel;
    private javax.swing.JButton muhasebeSilButon;
    private javax.swing.JTable muhasebeTablo;
    private com.toedter.calendar.JDateChooser muhasebeTarihSecici;
    private javax.swing.JPanel navigasyonPanel;
    private javax.swing.JButton randevuButon1;
    private javax.swing.JButton randevuGirisButon;
    private javax.swing.JPanel randevuPanel1;
    private javax.swing.JButton randevuSilIsletmeButon;
    private javax.swing.JTable randevuTablo;
    private javax.swing.JButton stokButon;
    private javax.swing.JPanel stokPanel;
    private javax.swing.JTable stokTablo;
    private com.toedter.calendar.JDateChooser tarihSecici;
    private javax.swing.JButton toplamAylikButon;
    private javax.swing.JButton toplamGunlukButon;
    private javax.swing.JButton urunCikar;
    private javax.swing.JButton urunEkle;
    private javax.swing.JButton urunMiktar;
    // End of variables declaration//GEN-END:variables
}
