using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;

namespace WindowsFormsApplication1.Classi
{
    class DataManager
    {
        private User[] users;
        private Movie[] movies;
        private Recommendation[] rec;
        private int[,] urm;


        public DataManager(Stream sUserMeta, Stream sItemMeta, Stream sTestSet)
        {
            loadUserMeta(sUserMeta);
            loadItemMeta(sItemMeta);
            loadTestSet(sTestSet);
        }

        public void loadUserMeta(Stream sUserMeta)
        {
            using (StreamReader sr = new StreamReader(sUserMeta))
            {
                if (sr.ReadLine() == null)
                {
                    sr.Close();
                    return;
                }
                List<User> us = new List<User>();
                string line = "";
                while ((line = sr.ReadLine()) != null)
                {
                    string[] fields = line.Split(',');
                    us.Add(new User() { id = int.Parse(fields[0]), gender = fields[1][0], age = int.Parse(fields[2]), occupation = int.Parse(fields[3]), zipCode = fields[4] });
                }
                users = new User[us.Last().id];
                foreach (var item in us)
                {
                    users[item.id - 1] = item;
                }
            }
        }

        public void loadItemMeta(Stream sItemMeta)
        {
            using (StreamReader sr = new StreamReader(sItemMeta))
            {
                if (sr.ReadLine() == null)
                {
                    sr.Close();
                    return;
                }
                List<Movie> us = new List<Movie>();
                string line = "";
                while ((line = sr.ReadLine()) != null)
                {
                    string[] fields = line.Split(',');
                    Movie entrance = new Movie() { id = int.Parse(fields[0]), title = fields[1], year = int.Parse(fields[2]), genre = fields[3].Split('|') };
                    entrance.genreWeight = new Dictionary<string, List<float>>();
                    foreach (var genre in entrance.genre)
                    {
                        entrance.genreWeight.Add(genre, new List<float>());
                    }
                    us.Add(entrance);
                }
                movies = new Movie[us.Last().id];
                foreach (var item in us)
                {
                    movies[item.id - 1] = item;
                }
            }
        }

        public void loadTestSet(Stream sTestSet)
        {
            using (StreamReader sr = new StreamReader(sTestSet))
            {
                if (sr.ReadLine() == null)
                {
                    sr.Close();
                    return;
                }
                urm = new int[users.Length, movies.Length];
                string line = "";
                while ((line = sr.ReadLine()) != null)
                {
                    string[] fields = line.Split(',');
                    int user = int.Parse(fields[0]) - 1;
                    int movie = int.Parse(fields[1]) - 1;
                    int rate = int.Parse(fields[2]);
                    urm[user, movie] = rate;
                    if (users[user].genre == null)
                    {
                        users[user].genre = new Dictionary<string, List<int>>();
                    }
                    if(movies[movie] != null)
                    {
                        foreach (var genre in movies[movie].genre)
                        {
                            if (!users[user].genre.ContainsKey(genre))
                            {
                                users[user].genre.Add(genre, new List<int>());
                            }                                
                            users[user].genre[genre].Add(rate);
                        }
                    }
                }

                for(int i=0; i<users.Length; i++)
                {
                    if (users[i] != null)
                    {
                        users[i].genreWeight = new Dictionary<string, float>();
                        foreach (var genre in users[i].genre)
                        {
                            float weight = 0f;
                            foreach (var rate in genre.Value)
                            {
                                weight += rate;
                            }
                            weight = weight / (genre.Value.Count() * 5);
                            users[i].genreWeight.Add(genre.Key, weight);
                        }
                    }
                }

                for (int i = 0; i < movies.Length; i++ )
                {
                    if (movies[i] != null)
                    {
                        int n_genre = movies[i].genre.Count();
                        float[] relevance = new float[n_genre];
                        for (int j = 0; j < users.Length; j++)
                        {
                            if (urm[j, i] != 0)
                            {
                                int k = 0;
                                foreach (var genre in movies[i].genreWeight)
                                {
                                    genre.Value.Add(urm[j, i] * users[j].genreWeight[genre.Key]);

                                    relevance[k] += users[j].genreWeight[genre.Key];
                                    k++;
                                }
                            }
                        }
                        int x = 0;
                        movies[i].genreWeightNormalized = new Dictionary<string, float>();
                        foreach (var genre in movies[i].genreWeight)
                        {
                            float par = 0f;
                            foreach (var rate in genre.Value)
                            {
                                par += rate;
                            }
                            if(relevance[x]==0)
                                movies[i].genreWeightNormalized.Add(genre.Key, 0);
                            else
                                movies[i].genreWeightNormalized.Add(genre.Key, par / (relevance[x] * 5));
                            x++;
                        }
                    }
                }
            }
        }

        public void loadTrainSet(Stream sTrainSet)
        {
            using (StreamReader sr = new StreamReader(sTrainSet))
            {
                if (sr.ReadLine() == null)
                {
                    sr.Close();
                    return;
                }
                List<Recommendation> us = new List<Recommendation>();
                string line = "";
                while ((line = sr.ReadLine()) != null)
                {
                    us.Add(new Recommendation() { user = int.Parse(line), rate = new int[5] });
                }
                rec = new Recommendation[us.Count()];
                int k = 0;
                foreach (var item in us)
                {
                    rec[k] = item;
                    k++;
                }
            }
        }

        public void createRecommendation()
        {
            for (int i = 0; i < rec.Length; i++)
            {
                int[] userRec = new int[5] {1, 1, 1, 1, 1};
                float[] userRecRate = new float[5] { 1f, 1f, 1f, 1f, 1f};
                User user = users[rec[i].user - 1];
                foreach (var movie in movies.Where(x => x != null))
                {
                    float par = 0f;
                    float den = 0f;
                    foreach (var genre in movie.genreWeightNormalized)
                    {
                        if (user.genreWeight.ContainsKey(genre.Key))
                        {
                            float userRate = user.genreWeight[genre.Key];
                            par += Math.Abs(userRate - genre.Value) * userRate;
                            den += userRate;
                        }
                    }
                    par = par / den;
                    int mov = movie.id;
                    for (int j = 0; j < 5; j++)
                    {
                        if (userRecRate[j] > par)
                        {
                            float tmp = userRecRate[j];
                            int tmpM = userRec[j];
                            userRecRate[j] = par;
                            userRec[j] = mov;
                            par = tmp;
                            mov = tmpM;
                        }
                    }
                }
                rec[i].rate = userRec;
            }
        }

        public void saveRecommendation(Stream sRec)
        {
            using (StreamWriter sw = new StreamWriter(sRec))
            {
                sw.WriteLine("\"UserId\",\"RecommendedMovieIds\"");
                for (int i = 0; i < rec.Length; i++)
                {
                    string line = "\"" + rec[i].user.ToString() + "\",\"";
                    for (int j = 0; j < 5; j++)
                    {
                        line += rec[i].rate[j].ToString();
                        if (j != 4)
                            line += " ";
                    }
                    line += "\"";
                    sw.WriteLine(line);
                }
            }
        }
    }
}
