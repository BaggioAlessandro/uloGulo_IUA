namespace WindowsFormsApplication1
{
    partial class Form1
    {
        /// <summary>
        /// Variabile di progettazione necessaria.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Pulire le risorse in uso.
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
            this.buttonUserMeta = new System.Windows.Forms.Button();
            this.buttonTestSet = new System.Windows.Forms.Button();
            this.buttonItemMeta = new System.Windows.Forms.Button();
            this.openUserMeta = new System.Windows.Forms.OpenFileDialog();
            this.openItemMeta = new System.Windows.Forms.OpenFileDialog();
            this.openTestSet = new System.Windows.Forms.OpenFileDialog();
            this.progressBar1 = new System.Windows.Forms.ProgressBar();
            this.buttonTrainSet = new System.Windows.Forms.Button();
            this.openTrainSet = new System.Windows.Forms.OpenFileDialog();
            this.saveResult = new System.Windows.Forms.SaveFileDialog();
            this.SuspendLayout();
            // 
            // buttonUserMeta
            // 
            this.buttonUserMeta.Location = new System.Drawing.Point(62, 49);
            this.buttonUserMeta.Name = "buttonUserMeta";
            this.buttonUserMeta.Size = new System.Drawing.Size(119, 41);
            this.buttonUserMeta.TabIndex = 0;
            this.buttonUserMeta.Text = "Load user meta";
            this.buttonUserMeta.UseVisualStyleBackColor = true;
            this.buttonUserMeta.Click += new System.EventHandler(this.buttonUserMeta_Click);
            // 
            // buttonTestSet
            // 
            this.buttonTestSet.Location = new System.Drawing.Point(312, 49);
            this.buttonTestSet.Name = "buttonTestSet";
            this.buttonTestSet.Size = new System.Drawing.Size(119, 41);
            this.buttonTestSet.TabIndex = 1;
            this.buttonTestSet.Text = "Load test set";
            this.buttonTestSet.UseVisualStyleBackColor = true;
            this.buttonTestSet.Click += new System.EventHandler(this.buttonTestSet_Click);
            // 
            // buttonItemMeta
            // 
            this.buttonItemMeta.Location = new System.Drawing.Point(187, 49);
            this.buttonItemMeta.Name = "buttonItemMeta";
            this.buttonItemMeta.Size = new System.Drawing.Size(119, 41);
            this.buttonItemMeta.TabIndex = 2;
            this.buttonItemMeta.Text = "Load item meta";
            this.buttonItemMeta.UseVisualStyleBackColor = true;
            this.buttonItemMeta.Click += new System.EventHandler(this.buttonItemMeta_Click);
            // 
            // openUserMeta
            // 
            this.openUserMeta.FileName = "openFileDialog1";
            this.openUserMeta.FileOk += new System.ComponentModel.CancelEventHandler(this.openUserMeta_FileOk);
            // 
            // openItemMeta
            // 
            this.openItemMeta.FileName = "openFileDialog1";
            this.openItemMeta.FileOk += new System.ComponentModel.CancelEventHandler(this.openItemMeta_FileOk);
            // 
            // openTestSet
            // 
            this.openTestSet.FileName = "openFileDialog1";
            this.openTestSet.FileOk += new System.ComponentModel.CancelEventHandler(this.openTestSet_FileOk);
            // 
            // progressBar1
            // 
            this.progressBar1.Location = new System.Drawing.Point(437, 49);
            this.progressBar1.Name = "progressBar1";
            this.progressBar1.Size = new System.Drawing.Size(472, 41);
            this.progressBar1.TabIndex = 3;
            // 
            // buttonTrainSet
            // 
            this.buttonTrainSet.Location = new System.Drawing.Point(312, 96);
            this.buttonTrainSet.Name = "buttonTrainSet";
            this.buttonTrainSet.Size = new System.Drawing.Size(119, 41);
            this.buttonTrainSet.TabIndex = 4;
            this.buttonTrainSet.Text = "Load train set";
            this.buttonTrainSet.UseVisualStyleBackColor = true;
            this.buttonTrainSet.Click += new System.EventHandler(this.buttonTrainSet_Click);
            // 
            // openTrainSet
            // 
            this.openTrainSet.FileName = "openFileDialog1";
            this.openTrainSet.FileOk += new System.ComponentModel.CancelEventHandler(this.openTrainSet_FileOk);
            // 
            // saveResult
            // 
            this.saveResult.FileOk += new System.ComponentModel.CancelEventHandler(this.saveResult_FileOk);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(921, 535);
            this.Controls.Add(this.buttonTrainSet);
            this.Controls.Add(this.progressBar1);
            this.Controls.Add(this.buttonItemMeta);
            this.Controls.Add(this.buttonTestSet);
            this.Controls.Add(this.buttonUserMeta);
            this.Name = "Form1";
            this.Text = "AUI:Technology";
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Button buttonUserMeta;
        private System.Windows.Forms.Button buttonTestSet;
        private System.Windows.Forms.Button buttonItemMeta;
        private System.Windows.Forms.OpenFileDialog openUserMeta;
        private System.Windows.Forms.OpenFileDialog openItemMeta;
        private System.Windows.Forms.OpenFileDialog openTestSet;
        private System.Windows.Forms.ProgressBar progressBar1;
        private System.Windows.Forms.Button buttonTrainSet;
        private System.Windows.Forms.OpenFileDialog openTrainSet;
        private System.Windows.Forms.SaveFileDialog saveResult;
    }
}

