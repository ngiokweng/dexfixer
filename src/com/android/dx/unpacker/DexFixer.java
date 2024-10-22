package com.android.dx.unpacker;

import com.android.dex.Dex;
import com.android.dex.util.FileUtils;
import com.android.dx.command.dexer.DxContext;
import com.android.dx.merge.CollisionPolicy;
import com.android.dx.merge.DexMerger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javafx.scene.shape.Path;

class DexFixer
{
    public static void main(String[] args) throws IOException
    {
        
    	if (args.length < 3)
        {
            printUsage();
            return;
        }

        String dexpath=args[0];
        String binpath=args[1];
        String outpath=args[2];
        
    	/*test
        String dexpath="C:\\Users\\user\\Desktop\\eclipse_test\\1398708_dexfile.dex";
        String binpath="C:\\Users\\user\\Desktop\\eclipse_test\\1398708_ins_merge.bin";
        String outpath="C:\\Users\\user\\Desktop\\eclipse_test\\out.dex";
        */
        
        File dexfile = new File(dexpath);
        File binfile = new File(binpath);
        if(!dexfile.exists() || !binfile.exists()){
            System.out.println("Usage: dexfile or binfile not found");
            return;
        }
        if (!dexfile.getPath().endsWith(".dex")) {
            System.out.println("Usage: DexFixer dexpath end not .dex");
            return;
        }

        Dex[] dexes = new Dex[1];
        dexes[0] = new Dex(dexfile);
        MethodCodeItemFile methodCodeItemFile = new MethodCodeItemFile(binfile);
        Dex merged = new DexMerger(dexes, CollisionPolicy.KEEP_FIRST, new DxContext(),
                methodCodeItemFile.getMethodCodeItems()).merge();
        merged.writeTo(new File(outpath));
        System.out.println("success");

    }

    private static void printUsage()
    {
        System.out.println("Usage: DexFixer <dex_file> <ins_bin> <output_path>");
    }
}
