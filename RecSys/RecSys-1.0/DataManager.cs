using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;
using System.Windows.Forms;

namespace RecSys_1._0
{
    class DataManager
    {
        private Dictionary<string, List<Recommendation>> urm;
        private int itemCount;
        private Double[,] simMat;

        public int loadUserMetaFromStream(Stream sUserMeta)
        {
            using (StreamReader userMeta = new StreamReader(sUserMeta))
            {
            }

            return 0;
        }

        public int loadItemMetaFromStream(Stream sItemMeta)
        {
            using (StreamReader itemMeta = new StreamReader(sItemMeta))
            {
            }

            return 0;
        }

        public int loadtrainSetFromStream(Stream strainSet)
        {
            using (StreamReader trainSet = new StreamReader(strainSet))
            {
                if (trainSet.ReadLine() == null)
                    return -1;
                urm = new Dictionary<string, List<Recommendation>>();
                List<Recommendation> rev = new List<Recommendation>();
                string line = trainSet.ReadLine();
                String[] tuple = line.Split(',');
                string lastItem = tuple[1];
                rev.Add(new Recommendation() { Name = tuple[0], Rate = Double.Parse(tuple[2]) });

                while ((line = trainSet.ReadLine()) != null)
                {
                    tuple = line.Split(',');
                    if (rev.Count == 0 || lastItem.Equals(tuple[1]))
                    {
                        rev.Add(new Recommendation() { Name = tuple[0], Rate = Double.Parse(tuple[2]) });
                    }
                    else
                    {
                        urm.Add(lastItem, rev);
                        lastItem = tuple[1];
                        rev = new List<Recommendation>();
                    }
                }

                urm.Add(lastItem, rev);

            }

            return 0;
        }

        public Double[,] similarityMatrix(ProgressBar pb)
        {
            itemCount = Int32.Parse(urm.Last().Key);
            simMat = new Double[itemCount,itemCount];
            pb.Value = 0;
            foreach (var item in urm)
            {
                int row = Int32.Parse(item.Key)-1;
                foreach (var subitem in urm)
                {
                    int col = Int32.Parse(subitem.Key)-1;
                    if (row == col)
                        simMat[row, col] = 1.0f;
                    else
                    {
                        simMat[row, col] = pSimilarityI1I2(item.Key, subitem.Key);
                        simMat[col, row] = simMat[row, col];
                    }
                }
                pb.Value = (int)((float)(row * 100) / itemCount);
            }
            pb.Value = 100;
            return simMat;
        }

        private Double pSimilarityI1I2(string item1, string item2)
        {
            List<Recommendation> sharedItems = new List<Recommendation>();
            //Prendo solo i rating fatti dalle stesse persone su item1 e item2
            foreach (var item in urm[item1])
            {
                if (urm[item2].Where(x => x.Name == item.Name).Count() != 0)
                {
                    sharedItems.Add(item);
                }
            }

            if (sharedItems.Count == 0)
                return 0;
            //Calcolo la media degli elementi votati in comune
            double prod1avg = 0.00f;/*
            foreach (Recommendation item in sharedItems)
            {
                prod1avg += urm[item1].Where(x => x.Name == item.Name).FirstOrDefault().Rate;
            }
            prod1avg = prod1avg / sharedItems.Count;
            */
            double prod2avg = 0.00f;/*
            foreach (Recommendation item in sharedItems)
            {
                prod2avg += urm[item2].Where(x => x.Name == item.Name).FirstOrDefault().Rate;
            }
            prod2avg = prod2avg / sharedItems.Count;
            */
            foreach (Recommendation item in sharedItems)
            {
                prod1avg += urm[item1].Where(x => x.Name == item.Name).FirstOrDefault().Rate;
                prod2avg += urm[item2].Where(x => x.Name == item.Name).FirstOrDefault().Rate;
            }
            prod1avg = prod1avg / sharedItems.Count;
            prod2avg = prod2avg / sharedItems.Count;
            //Calcolo la varianza
            double prod1var = 0.0f;
            double prod2var = 0.0f;
            double cov = 0.0f;
            foreach (Recommendation item in sharedItems)
            {
                prod1var += Math.Pow(urm[item1].Where(x => x.Name == item.Name).FirstOrDefault().Rate - prod1avg, 2);
                prod2var += Math.Pow(urm[item2].Where(x => x.Name == item.Name).FirstOrDefault().Rate - prod2avg, 2);
                cov += (urm[item1].Where(x => x.Name == item.Name).FirstOrDefault().Rate - prod1avg) * (urm[item2].Where(x => x.Name == item.Name).FirstOrDefault().Rate - prod2avg);
            }

            double pi = 0f;
            if(prod1var!=0 && prod2var!=0)
                pi = cov/Math.Sqrt(prod1var*prod2var);

            return pi;
        }

        public void saveSimMatrix(string path)
        {
            using (StreamWriter sw = new StreamWriter(path))
            {
                for (int row = 0; row < itemCount; row++)
                {
                    String line = "";
                    for (int col = 0; col < itemCount; col++)
                    {
                        line += simMat[row, col].ToString() + ';';
                    }

                    sw.WriteLine(line.Remove(line.LastIndexOf(';'), 1));
                }
            }
        }
    }
}
