import numpy as np
from sklearn.decomposition import PCA


R = np.array([
    [1, 0.577, 0.509, 0.387, 0.462],
    [0.577, 1, 0.599, 0.389, 0.322],
    [0.509, 0.599, 1, 0.436, 0.426],
    [0.387, 0.389, 0.436, 1, 0.523],
    [0.462, 0.322, 0.426, 0.523, 1]
])

print(R)


# PCA modelini oluşturma
pca = PCA()

# PCA'yı matrise uygulama
pca.fit(R)

# Temel bileşenleri (eigenvektörler) yazdırma
print("Temel Bileşenler (Eigenvektörler):")
print(pca.components_)

# Açıklanan varyans oranlarını yazdırma
print("\nAçıklanan Varyans Oranları:")
print(pca.explained_variance_ratio_)

# Tekil değerler
print("\nTekil değerler:")
print(pca.singular_values_)


#pca modeli dışında elle hesaplamak için özdeğer ve özvektör
print("\nözdegerler ve ozvektorler")
ozdegerler, ozvektorler = np.linalg.eig(R)
print("Özdeğerler:")
print(ozdegerler)


toplam = 0
for i in ozdegerler:
    toplam_ozdegerler= sum(ozdegerler)
    x= i/toplam_ozdegerler
    print(x)
    print("Toplam varyansı açıklama oranı")
    #kümülatif
    toplam=x*100 + toplam
    print(toplam)
    print("\n---------------------------------------------")


# Özvektörleri yazdırma
print("\nÖzvektörler:")
print(ozvektorler)

