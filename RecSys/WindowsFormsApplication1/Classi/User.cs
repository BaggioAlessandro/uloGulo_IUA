using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace WindowsFormsApplication1.Classi
{
    class User
    {
        public int id;
        public char gender;
        public string zipCode;
        public int occupation;
        public int age;

        public Dictionary<string, List<int>> genre;
        public Dictionary<string, float> genreWeight;
    }
}
