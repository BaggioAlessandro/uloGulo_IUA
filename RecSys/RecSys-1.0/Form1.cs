using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.IO;

namespace RecSys_1._0
{
    public partial class Form1 : Form
    {
        private Stream userMeta;
        private Stream itemMeta;
        private Stream trainSet;
        private DataManager dm;

        public Form1()
        {
            InitializeComponent();
            dm = new DataManager();
            userMeta = null;
            itemMeta = null;
            trainSet = null;
            String curDir = Directory.GetCurrentDirectory();
            openUserMeta.InitialDirectory = curDir;
            openItemMeta.InitialDirectory = curDir;
            openTrainSet.InitialDirectory = curDir;
        }

        private void openUserMeta_FileOk(object sender, CancelEventArgs e)
        {
            textUserMeta.Text = openUserMeta.FileName;
            userMeta = openUserMeta.OpenFile();
            dm.loadUserMetaFromStream(userMeta);
        }

        private void buttonUserMeta_Click(object sender, EventArgs e)
        {
            if (!textUserMeta.Text.Equals(""))
                openUserMeta.InitialDirectory = textUserMeta.Text;
            openUserMeta.ShowDialog();
        }

        private void buttonItemMeta_Click(object sender, EventArgs e)
        {
            if (!textItemMeta.Text.Equals(""))
                openItemMeta.InitialDirectory = textItemMeta.Text;
            openItemMeta.ShowDialog();
        }

        private void buttonTrainSet_Click(object sender, EventArgs e)
        {
            if (!textItemMeta.Text.Equals(""))
                openTrainSet.InitialDirectory = textTrainSet.Text;
            openTrainSet.ShowDialog();
        }

        private void openItemMeta_FileOk(object sender, CancelEventArgs e)
        {
            textItemMeta.Text = openItemMeta.FileName;
            itemMeta = openItemMeta.OpenFile();
            dm.loadItemMetaFromStream(itemMeta);
        }

        private void openTrainSet_FileOk(object sender, CancelEventArgs e)
        {
            textTrainSet.Text = openTrainSet.FileName;
            trainSet = openTrainSet.OpenFile();
            dm.loadtrainSetFromStream(trainSet);
        }

        private void buttonComputeSimMat_Click(object sender, EventArgs e)
        {
            labelComputeSimMat.Text = "Computing";
            Double[,] simMat = dm.similarityMatrix(progressComputeSimMat);
            labelComputeSimMat.Text = "Computed";
            ((Button)sender).Text = "Save Similarity Matrix";
            ((Button)sender).Click += buttonSaveSimMat_Click;
            ((Button)sender).Click -= buttonComputeSimMat_Click;
        }

        private void buttonSaveSimMat_Click(object sender, EventArgs e)
        {
            saveSimilarityMatrix.ShowDialog();
        }

        private void saveSimilarityMatrix_FileOk(object sender, CancelEventArgs e)
        {
            dm.saveSimMatrix(saveSimilarityMatrix.FileName);
        }
    }
}
