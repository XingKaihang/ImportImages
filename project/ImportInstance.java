import hipi.image.FloatImage;
import hipi.image.ImageHeader;
import hipi.imagebundle.mapreduce.ImageBundleInputFormat;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import hipi.imagebundle.HipiImageBundle;
import hipi.imagebundle.AbstractImageBundle;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;
import java.util.*;
import java.io.FileWriter;
import hipi.image.ImageHeader;
import hipi.image.ImageHeader.ImageType;
import org.apache.hadoop.fs.FileSystem;


public class ImportInstance {

  
  public static void main(String[] args) throws Exception {
      File folder = new File(args[0]);
      File[] files = folder.listFiles();

      Configuration conf = new Configuration();
      HipiImageBundle hib = new HipiImageBundle(new Path("import_temp.hib"), conf);
      hib.open(AbstractImageBundle.FILE_MODE_WRITE, true);

      for (File file : files) 
	  {
	      FileInputStream fis = new FileInputStream(file);
	      String fileName = file.getName().toLowerCase();
	      String suffix = fileName.substring(fileName.lastIndexOf('.'));
	      if (suffix.compareTo(".jpg") == 0 || suffix.compareTo(".jpeg") == 0) 
		  {
		      hib.addImage(fis, ImageType.JPEG_IMAGE);
		  }
	      else if (suffix.compareTo(".png") == 0)
		  {
		      hib.addImage(fis, ImageType.PNG_IMAGE);
		  }
	      System.out.println(" ** add: " + fileName);
	  }
	  hib.close();

	  HipiImageBundle hib_re = new HipiImageBundle(new Path("import_temp.hib"), conf);

	  Path path = new Path(args[1]);
	  HipiImageBundle original_hib = new HipiImageBundle(path, conf);

	  HipiImageBundle hib_merge = new HipiImageBundle(new Path("import_temp_merge.hib"), conf);
	  hib_merge.open(AbstractImageBundle.FILE_MODE_WRITE, true);

	  hib_merge.append(original_hib);
      hib_merge.append(hib_re);

      hib_merge.close();
      Path original_dataPath = new Path(path.toString() + ".dat");

      FileSystem fileSystem = FileSystem.get(conf);
      Path indexPath = hib_re.getPath();
      Path dataPath = new Path(indexPath.toString() + ".dat");
      fileSystem.delete(indexPath, false);
      fileSystem.delete(dataPath, false);


      HipiImageBundle new_original = new HipiImageBundle(original_hib.getPath(), conf);
      HipiImageBundle import_re = new HipiImageBundle(new Path("import_temp_merge.hib"), conf);
	  new_original.open(AbstractImageBundle.FILE_MODE_WRITE, true);
	  new_original.append(import_re);

      indexPath = hib_merge.getPath();
      dataPath = new Path(indexPath.toString() + ".dat");
      fileSystem.delete(indexPath, false);
      fileSystem.delete(dataPath, false);

      System.out.println("append: images");
      System.exit(0);
  }


}