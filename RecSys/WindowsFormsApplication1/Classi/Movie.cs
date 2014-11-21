using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace WindowsFormsApplication1.Classi
{
    class Movie
    {
        public int id;
        public string[] genre;
        public int year;
        public string title;
        public Dictionary<string, List<float>> genreWeight;
        public Dictionary<string, float> genreWeightNormalized;
    }
}
