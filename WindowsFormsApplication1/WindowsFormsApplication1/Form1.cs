using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.IO;

namespace WindowsFormsApplication1
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            using(StreamReader sr = new StreamReader("C:\\Users\\ASUS\\Desktop\\AUI_Cremonesi\\uloGulo_IUA\\MatLab\\SVD_Puro\\rec.csv")){
                using (StreamReader srTest = new StreamReader("C:\\Users\\ASUS\\Desktop\\AUI_Cremonesi\\uloGulo_IUA\\DataSet\\test.csv"))
                {
                    string line = srTest.ReadLine();
                    using(StreamWriter sw = new StreamWriter("C:\\Users\\ASUS\\Desktop\\AUI_Cremonesi\\uloGulo_IUA\\DataSet\\res.csv")){
                        sw.WriteLine("UserId,RecommendedMovieIds");
                        
                        while ((line = srTest.ReadLine()) != null)
                        {
                            line += ",";
                            string[] rates = sr.ReadLine().Split(',');
                            for (int i = 0; i < 5; i++)
                            {
                                line += rates[i] + ' ';
                            }
                            line = line.Remove(line.LastIndexOf(' '));
                            sw.WriteLine(line);
                        }
                    }
                }
            }
        }
    }
}
