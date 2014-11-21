using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.IO;

namespace WindowsFormsApplication1
{
    public partial class Form1 : Form
    {
        private Stream streamUserMeta = null;
        private Stream streamItemMeta = null;
        private Stream streamTestSet = null;
        private Stream streamTrainSet = null;
        private Stream streamSaveResult = null;
        private Classi.DataManager dm;

        public Form1()
        {
            InitializeComponent();
        }

        private void buttonUserMeta_Click(object sender, EventArgs e)
        {
            openUserMeta.ShowDialog();
        }

        private void buttonItemMeta_Click(object sender, EventArgs e)
        {
            openItemMeta.ShowDialog();
        }

        private void buttonTestSet_Click(object sender, EventArgs e)
        {
            openTestSet.ShowDialog();
        }

        private void openUserMeta_FileOk(object sender, CancelEventArgs e)
        {
            streamUserMeta = openUserMeta.OpenFile();
        }

        private void openItemMeta_FileOk(object sender, CancelEventArgs e)
        {
            streamItemMeta = openItemMeta.OpenFile();
        }

        private void openTestSet_FileOk(object sender, CancelEventArgs e)
        {
            streamTestSet = openTestSet.OpenFile();
            dm = new Classi.DataManager(streamUserMeta, streamItemMeta, streamTestSet);
        }

        private void buttonTrainSet_Click(object sender, EventArgs e)
        {
            openTrainSet.ShowDialog();
        }

        private void openTrainSet_FileOk(object sender, CancelEventArgs e)
        {
            streamTrainSet = openTrainSet.OpenFile();
            dm.loadTrainSet(streamTrainSet);
            dm.createRecommendation();
            saveResult.ShowDialog();
        }

        private void saveResult_FileOk(object sender, CancelEventArgs e)
        {
            streamSaveResult = saveResult.OpenFile();
            dm.saveRecommendation(streamSaveResult);
        }
    }
}
