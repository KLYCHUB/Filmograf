package com.example.filmograf;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class VizyonFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_vizyonda_filmler, container, false);

        GridView vizyondaFilmlerGridView = rootView.findViewById(R.id.vizyonda_filmler_gridview);
        setupGridView(vizyondaFilmlerGridView);

        return rootView;
    }

    private void setupGridView(GridView gridView) {
        final ArrayList<Film> vizyondaFilmListesi = new ArrayList<>();
        vizyondaFilmListesi.add(new Film("Zodiac", "Gerilim, Suç", "1960'larda San Francisco'yu kasıp kavuran Zodiac katilini anlatan bir film.", "https://www.youtube.com/watch?v=XYZ", R.drawable.zodiac, "18 Mayıs 2007", "Jake Gyllenhaal, Robert Downey Jr., Mark Ruffalo"));
        vizyondaFilmListesi.add(new Film("Poor Things", "Komedi, Dram, Fantastik", "19. yüzyılda canlanan bir ölü, bir yazarla birlikte cinayetleri çözmeye çalışır.", "https://www.youtube.com/watch?v=RlbR5N6veqw", R.drawable.poor_things, "9 Şubat 2024", "Emma Stone, Nicholas Hoult, Willem Dafoe"));
        vizyondaFilmListesi.add(new Film("Killers of the Flower Moon", "Suç, Dram, Western", "1920'lerde Oklahoma'da yaşanan Osage cinayetlerini anlatan bir film.", "https://www.youtube.com/watch?v=f1oqFhyjebw", R.drawable.killers_flower_moon, "20 Ekim 2023", "Leonardo DiCaprio, Robert De Niro, Jesse Plemons"));
        vizyondaFilmListesi.add(new Film("Spy Game", "Casusluk, Gerilim", "Emekli bir casus, eski bir öğrencisini kurtarmak için CIA ile mücadele eder.", "https://www.youtube.com/watch?v=M1f0c3utHGI", R.drawable.spy_game, "15 Aralık 2001", "Robert Redford, Brad Pitt, Catherine Zeta-Jones"));
        vizyondaFilmListesi.add(new Film("Soysuzlar Çetesi", "Savaş, Dram", "İkinci Dünya Savaşı sırasında Nazi karşıtı bir grup tarafından kurulan bir intikam çetesinin hikayesi.", "https://www.youtube.com/watch?v=WU9w4Bxp2wg", R.drawable.soysuzlar_cetesi, "21 Ağustos 2009", "Brad Pitt, Christoph Waltz, Mélanie Laurent"));
        vizyondaFilmListesi.add(new Film("John Wick 4", "Aksiyon", "John Wick'in yeni maceraları.", "https://www.youtube.com/watch?v=XYZ", R.drawable.john_wick_4, "24 Mart 2023", "Keanu Reeves, Laurence Fishburne"));
        vizyondaFilmListesi.add(new Film("Dune", "Bilim Kurgu", "Çöl gezegeni Arrakis'te geçen bir hikaye.", "https://www.youtube.com/watch?v=ABC", R.drawable.dune, "22 Ekim 2021", "Timothée Chalamet, Zendaya"));
        vizyondaFilmListesi.add(new Film("Her (2013)", "Dram, Romantik, Bilim Kurgu", "Yapay zeka bir kadın sesi olan Samantha ile arasında özel bir bağ oluşan bir adamın hikayesi.", "https://www.youtube.com/watch?v=dJTU48_yghs&pp=ygUKSGVyICgyMDEzKQ%3D%3D", R.drawable.her, "10 Ocak 2014", "Joaquin Phoenix, Scarlett Johansson"));
        vizyondaFilmListesi.add(new Film("District 9 (2009)", "Bilim Kurgu", "Dünyaya yerleşen uzaylı ırkının yaşadığı Yasak Bölge 9'da gelişen olaylar.", "https://www.youtube.com/watch?v=DyLUwOcR5pk&pp=ygUJZGlzdGlyYyA5", R.drawable.district_9, "14 Ağustos 2009", "Sharlto Copley, Jason Cope"));
        vizyondaFilmListesi.add(new Film("Enzo: Yağmurda Yarış Sanatı", "Dram, Spor", "Denny ,deneyimlerinden ders alarak dünyayı hayatını şekillendirmektedir.", "https://m.youtube.com/watch?v=9gsn_0tGVL4", R.drawable.enzo_yagmurd_yar_sanati, "7 Ağustos 2019", "Tom Hanks, Milo Ventimiglia"));
        FilmAdapter filmAdapter = new FilmAdapter(requireContext(), vizyondaFilmListesi);
        gridView.setAdapter(filmAdapter);

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            // Video izleme dialogunu buraya da ekleyebilirsiniz.
        });
    }

    private static class Film {
        private final String isim;
        private final String tur;
        private final String aciklama;
        private final String videoUrl;
        private final int resimId;
        private final String vizyonTarihi;
        private final String oyuncular;

        Film(String isim, String tur, String aciklama, String videoUrl, int resimId, String vizyonTarihi, String oyuncular) {
            this.isim = isim;
            this.tur = tur;
            this.aciklama = aciklama;
            this.videoUrl = videoUrl;
            this.resimId = resimId;
            this.vizyonTarihi = vizyonTarihi;
            this.oyuncular = oyuncular;
        }

        public String getIsim() {
            return isim;
        }

        public String getTur() {
            return tur;
        }

        public String getAciklama() {
            return aciklama;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public int getResimId() {
            return resimId;
        }

        public String getVizyonTarihi() {
            return vizyonTarihi;
        }

        public String getOyuncular() {
            return oyuncular;
        }
    }

    private static class FilmAdapter extends BaseAdapter {

        private final Context context;
        private final ArrayList<Film> filmListesi;

        FilmAdapter(Context context, ArrayList<Film> filmListesi) {
            this.context = context;
            this.filmListesi = filmListesi;
        }

        @Override
        public int getCount() {
            return filmListesi.size();
        }

        @Override
        public Object getItem(int position) {
            return filmListesi.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.grid_item_film, parent, false);
            }

            Film film = (Film) getItem(position);

            TextView filmIsim = convertView.findViewById(R.id.film_isim);
            ImageView filmResim = convertView.findViewById(R.id.film_resim);
            TextView filmAciklama = convertView.findViewById(R.id.film_aciklama);
            TextView filmVizyonTarihi = convertView.findViewById(R.id.film_vizyon_tarihi);
            TextView filmOyuncular = convertView.findViewById(R.id.film_oyuncular);

            filmIsim.setText(film.getIsim());
            filmResim.setImageResource(film.getResimId());
            filmAciklama.setText(film.getAciklama());
            filmVizyonTarihi.setText(film.getVizyonTarihi());
            filmOyuncular.setText(film.getOyuncular());

            return convertView;
        }
    }
}
