namespace RecSys_1._0
{
    partial class Form1
    {
        /// <summary>
        /// Variabile di progettazione necessaria.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Liberare le risorse in uso.
        /// </summary>
        /// <param name="disposing">ha valore true se le risorse gestite devono essere eliminate, false in caso contrario.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Codice generato da Progettazione Windows Form

        /// <summary>
        /// Metodo necessario per il supporto della finestra di progettazione. Non modificare
        /// il contenuto del metodo con l'editor di codice.
        /// </summary>
        private void InitializeComponent()
        {
            this.openUserMeta = new System.Windows.Forms.OpenFileDialog();
            this.textUserMeta = new System.Windows.Forms.TextBox();
            this.buttonUserMeta = new System.Windows.Forms.Button();
            this.openItemMeta = new System.Windows.Forms.OpenFileDialog();
            this.openTrainSet = new System.Windows.Forms.OpenFileDialog();
            this.buttonItemMeta = new System.Windows.Forms.Button();
            this.textItemMeta = new System.Windows.Forms.TextBox();
            this.buttonTrainSet = new System.Windows.Forms.Button();
            this.textTrainSet = new System.Windows.Forms.TextBox();
            this.progressComputeSimMat = new System.Windows.Forms.ProgressBar();
            this.buttonComputeSimMat = new System.Windows.Forms.Button();
            this.labelComputeSimMat = new System.Windows.Forms.Label();
            this.saveSimilarityMatrix = new System.Windows.Forms.SaveFileDialog();
            this.SuspendLayout();
            // 
            // openUserMeta
            // 
            this.openUserMeta.FileOk += new System.ComponentModel.CancelEventHandler(this.openUserMeta_FileOk);
            // 
            // textUserMeta
            // 
            this.textUserMeta.Location = new System.Drawing.Point(130, 11);
            this.textUserMeta.Name = "textUserMeta";
            this.textUserMeta.Size = new System.Drawing.Size(500, 20);
            this.textUserMeta.TabIndex = 0;
            // 
            // buttonUserMeta
            // 
            this.buttonUserMeta.Location = new System.Drawing.Point(8, 9);
            this.buttonUserMeta.Name = "buttonUserMeta";
            this.buttonUserMeta.Size = new System.Drawing.Size(116, 23);
            this.buttonUserMeta.TabIndex = 1;
            this.buttonUserMeta.Text = "Load UserMeta";
            this.buttonUserMeta.UseVisualStyleBackColor = true;
            this.buttonUserMeta.Click += new System.EventHandler(this.buttonUserMeta_Click);
            // 
            // openItemMeta
            // 
            this.openItemMeta.FileOk += new System.ComponentModel.CancelEventHandler(this.openItemMeta_FileOk);
            // 
            // openTrainSet
            // 
            this.openTrainSet.FileOk += new System.ComponentModel.CancelEventHandler(this.openTrainSet_FileOk);
            // 
            // buttonItemMeta
            // 
            this.buttonItemMeta.Location = new System.Drawing.Point(8, 35);
            this.buttonItemMeta.Name = "buttonItemMeta";
            this.buttonItemMeta.Size = new System.Drawing.Size(116, 23);
            this.buttonItemMeta.TabIndex = 3;
            this.buttonItemMeta.Text = "Load ItemMeta";
            this.buttonItemMeta.UseVisualStyleBackColor = true;
            this.buttonItemMeta.Click += new System.EventHandler(this.buttonItemMeta_Click);
            // 
            // textItemMeta
            // 
            this.textItemMeta.Location = new System.Drawing.Point(130, 37);
            this.textItemMeta.Name = "textItemMeta";
            this.textItemMeta.Size = new System.Drawing.Size(500, 20);
            this.textItemMeta.TabIndex = 2;
            // 
            // buttonTrainSet
            // 
            this.buttonTrainSet.Location = new System.Drawing.Point(8, 61);
            this.buttonTrainSet.Name = "buttonTrainSet";
            this.buttonTrainSet.Size = new System.Drawing.Size(116, 23);
            this.buttonTrainSet.TabIndex = 5;
            this.buttonTrainSet.Text = "Load TestSet";
            this.buttonTrainSet.UseVisualStyleBackColor = true;
            this.buttonTrainSet.Click += new System.EventHandler(this.buttonTrainSet_Click);
            // 
            // textTrainSet
            // 
            this.textTrainSet.Location = new System.Drawing.Point(130, 63);
            this.textTrainSet.Name = "textTrainSet";
            this.textTrainSet.Size = new System.Drawing.Size(500, 20);
            this.textTrainSet.TabIndex = 4;
            // 
            // progressComputeSimMat
            // 
            this.progressComputeSimMat.Location = new System.Drawing.Point(13, 149);
            this.progressComputeSimMat.Name = "progressComputeSimMat";
            this.progressComputeSimMat.Size = new System.Drawing.Size(733, 23);
            this.progressComputeSimMat.TabIndex = 6;
            // 
            // buttonComputeSimMat
            // 
            this.buttonComputeSimMat.Location = new System.Drawing.Point(300, 89);
            this.buttonComputeSimMat.Name = "buttonComputeSimMat";
            this.buttonComputeSimMat.Size = new System.Drawing.Size(121, 54);
            this.buttonComputeSimMat.TabIndex = 7;
            this.buttonComputeSimMat.Text = "Compute Similarity Matrix";
            this.buttonComputeSimMat.UseVisualStyleBackColor = true;
            this.buttonComputeSimMat.Click += new System.EventHandler(this.buttonComputeSimMat_Click);
            // 
            // labelComputeSimMat
            // 
            this.labelComputeSimMat.AutoSize = true;
            this.labelComputeSimMat.Location = new System.Drawing.Point(427, 130);
            this.labelComputeSimMat.Name = "labelComputeSimMat";
            this.labelComputeSimMat.Size = new System.Drawing.Size(75, 13);
            this.labelComputeSimMat.TabIndex = 8;
            this.labelComputeSimMat.Text = "Not Computed";
            // 
            // saveSimilarityMatrix
            // 
            this.saveSimilarityMatrix.FileOk += new System.ComponentModel.CancelEventHandler(this.saveSimilarityMatrix_FileOk);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(758, 262);
            this.Controls.Add(this.labelComputeSimMat);
            this.Controls.Add(this.buttonComputeSimMat);
            this.Controls.Add(this.progressComputeSimMat);
            this.Controls.Add(this.buttonTrainSet);
            this.Controls.Add(this.textTrainSet);
            this.Controls.Add(this.buttonItemMeta);
            this.Controls.Add(this.textItemMeta);
            this.Controls.Add(this.buttonUserMeta);
            this.Controls.Add(this.textUserMeta);
            this.Name = "Form1";
            this.Text = "Form1";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.OpenFileDialog openUserMeta;
        private System.Windows.Forms.TextBox textUserMeta;
        private System.Windows.Forms.Button buttonUserMeta;
        private System.Windows.Forms.OpenFileDialog openItemMeta;
        private System.Windows.Forms.OpenFileDialog openTrainSet;
        private System.Windows.Forms.Button buttonItemMeta;
        private System.Windows.Forms.TextBox textItemMeta;
        private System.Windows.Forms.Button buttonTrainSet;
        private System.Windows.Forms.TextBox textTrainSet;
        private System.Windows.Forms.ProgressBar progressComputeSimMat;
        private System.Windows.Forms.Button buttonComputeSimMat;
        private System.Windows.Forms.Label labelComputeSimMat;
        private System.Windows.Forms.SaveFileDialog saveSimilarityMatrix;
    }
}

