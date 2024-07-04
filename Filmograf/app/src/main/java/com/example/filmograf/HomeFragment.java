package com.example.filmograf;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        ListView filmListView = rootView.findViewById(R.id.film_listview);
        setupListView(filmListView);

        return rootView;
    }

    private void setupListView(ListView listView) {
        final ArrayList<Film> filmListesi = new ArrayList<>();
        filmListesi.add(new Film("Yüzüklerin Efendisi", "Fantastik", "Tek Yüzük'ü yok etmek için yola çıkan bir hobbit ve arkadaşlarının hikayesini anlatan destansı bir film serisi.", "https://youtu.be/su9oRo2ZN2c", R.drawable.yuzuklerin_efendisi, 9.0f));
        filmListesi.add(new Film("Mad Max", "Aksiyon", "Furiosa, George Miller tarafından yönetilen ve birlikte yazılan ve Anya Taylor-Joy'un Furiosa karakterini canlandırdığı bir Avustralya kıyamet sonrası aksiyon macera filmi.", "https://www.youtube.com/watch?v=hEJnMQG9ev8&ab_channel=WarnerBros.Pictures", R.drawable.madmax, 8.1f));
        filmListesi.add(new Film("Matrix", "Bilim Kurgu", "Gerçek dünya olarak algılanan şeyin aslında insanları kontrol altında tutan bir simülasyon olduğunu keşfeden bir adamın hikayesini anlatan bilim kurgu filmidir.", "https://www.youtube.com/watch?v=m8e-FF8MsqU", R.drawable.matrix, 8.7f));
        filmListesi.add(new Film("Inception", "Aksiyon", "Rüya içinde rüya görerek insanların bilinçaltına girip fikirler yerleştirmeyi veya çalmayı konu alan karmaşık bir hikayeye sahip bilim kurgu filmidir.", "https://www.youtube.com/watch?v=YoHD9XEInc0", R.drawable.inception, 8.8f));
        filmListesi.add(new Film("Esaretin Bedeli", "Drama", "Bir adamın haksız yere cinayetle suçlanıp hapishaneye gönderilmesi ve orada yaşadığı zorlukları anlatan bir film.", "https://www.youtube.com/watch?v=7BG-BpvkYB8", R.drawable.esaretinbedeli, 9.3f));
        filmListesi.add(new Film("Yıldızlararası", "Bilim Kurgu", "Dünya'nın yaşanmaz hale gelmesi üzerine bir grup astronotun yeni bir yuva bulmak için uzaya yolculuğa çıktıkları bir film.", "https://www.youtube.com/watch?v=zSWdZVtXT7E", R.drawable.yildizlararasi, 8.7f));
        filmListesi.add(new Film("Batman", "Süper Kahraman", "Gotham şehrini suçlulardan korumak için mücadele eden bir süper kahramanın hikayesini anlatan film serisi.", "https://www.youtube.com/watch?v=LDG9bisJEaI", R.drawable.batman, 9f));

        FilmAdapter filmAdapter = new FilmAdapter(requireContext(), filmListesi);
        listView.setAdapter(filmAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Film selectedFilm = filmListesi.get(position);
                showVideoDialog(selectedFilm);
            }
        });
    }

    private void showVideoDialog(Film selectedFilm) {
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        View dialogView = inflater.inflate(R.layout.dialog_video, null);
        WebView webViewFragman = dialogView.findViewById(R.id.webview_fragman);

        webViewFragman.setWebViewClient(new WebViewClient());
        webViewFragman.getSettings().setJavaScriptEnabled(true);
        webViewFragman.loadUrl(selectedFilm.getVideoUrl());

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(dialogView);

        builder.setPositiveButton("Kapat", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                webViewFragman.stopLoading();
                dialog.dismiss();
                webViewFragman.destroy();
            }
        });
        builder.show();
    }

    private static class Film {
        private float genelPuan;
        private String isim;
        private String tur;
        private String aciklama;
        private String videoUrl;
        private int resimId;

        Film(String isim, String tur, String aciklama, String videoUrl, int resimId, float genelPuan) {
            this.isim = isim;
            this.tur = tur;
            this.aciklama = aciklama;
            this.videoUrl = videoUrl;
            this.resimId = resimId;
            this.genelPuan = genelPuan;
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

        public float getGenelPuan() {
            return genelPuan;
        }
    }

    private class FilmAdapter extends BaseAdapter {

        private Context context;
        private ArrayList<Film> filmListesi;

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
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.list_item_film, parent, false);
                holder = new ViewHolder();
                holder.filmIsim = convertView.findViewById(R.id.film_isim);
                holder.filmResim = convertView.findViewById(R.id.film_resim);
                holder.filmAciklama = convertView.findViewById(R.id.film_aciklama);
                holder.imdbPuan = convertView.findViewById(R.id.imdb_puan);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final Film film = (Film) getItem(position);

            holder.filmIsim.setText(film.getIsim());
            holder.filmResim.setImageResource(film.getResimId());
            holder.filmAciklama.setText(film.getAciklama());
            holder.imdbPuan.setText(String.format("IMDB: %.2f/10", film.getGenelPuan()));

            holder.filmResim.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showVideoDialog(film);
                }
            });

            return convertView;
        }

        private class ViewHolder {
            TextView filmIsim;
            ImageView filmResim;
            TextView filmAciklama;
            TextView imdbPuan;
        }
    }

}
