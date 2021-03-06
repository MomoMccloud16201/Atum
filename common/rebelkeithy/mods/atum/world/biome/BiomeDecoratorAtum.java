package rebelkeithy.mods.atum.world.biome;

import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.GRASS;
import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.LAKE;
import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.SAND_PASS2;
import static net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.COAL;
import static net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.DIAMOND;
import static net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.GOLD;
import static net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.IRON;
import static net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.LAPIS;
import static net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.REDSTONE;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;
import net.minecraft.world.gen.feature.WorldGenCactus;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenLiquids;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenReed;
import net.minecraft.world.gen.feature.WorldGenSand;
import net.minecraft.world.gen.feature.WorldGenWaterlily;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
import rebelkeithy.mods.atum.Atum;
import rebelkeithy.mods.atum.AtumBlocks;
import rebelkeithy.mods.atum.world.decorators.WorldGenShrub;

public class BiomeDecoratorAtum extends BiomeDecorator
{    

    private float shrubChance;

	public BiomeDecoratorAtum(BiomeGenBase par1BiomeGenBase)
    {
    	super(par1BiomeGenBase);
        this.sandGen = new WorldGenSand(7, AtumBlocks.sand.blockID);
        this.gravelAsSandGen = new WorldGenSand(6, Block.gravel.blockID);
        this.dirtGen = new WorldGenMinable(Block.dirt.blockID, 32);
        this.gravelGen = new WorldGenMinable(Block.gravel.blockID, 32);
        this.coalGen = new WorldGenMinable(AtumBlocks.coalOre.blockID, 16, AtumBlocks.stone.blockID);
        this.ironGen = new WorldGenMinable(AtumBlocks.ironOre.blockID, 8, AtumBlocks.stone.blockID);
        this.goldGen = new WorldGenMinable(AtumBlocks.goldOre.blockID, 8, AtumBlocks.stone.blockID);
        this.redstoneGen = new WorldGenMinable(AtumBlocks.redstoneOre.blockID, 7, AtumBlocks.stone.blockID);
        this.diamondGen = new WorldGenMinable(AtumBlocks.diamondOre.blockID, 7, AtumBlocks.stone.blockID);
        this.lapisGen = new WorldGenMinable(AtumBlocks.lapisOre.blockID, 6);
        this.plantYellowGen = new WorldGenFlowers(Block.plantYellow.blockID);
        this.plantRedGen = new WorldGenFlowers(Block.plantRed.blockID);
        this.mushroomBrownGen = new WorldGenFlowers(Block.mushroomBrown.blockID);
        this.mushroomRedGen = new WorldGenFlowers(Block.mushroomRed.blockID);
        this.bigMushroomGen = new WorldGenBigMushroom();
        this.reedGen = new WorldGenReed();
        this.cactusGen = new WorldGenCactus();
        this.waterlilyGen = new WorldGenWaterlily();
        this.waterlilyPerChunk = 0;
        this.treesPerChunk = 0;
        this.flowersPerChunk = 2;
        this.grassPerChunk = 1;
        this.deadBushPerChunk = 1;
        this.shrubChance = 0.3F;
        this.mushroomsPerChunk = 0;
        this.reedsPerChunk = 0;
        this.cactiPerChunk = 0;
        this.sandPerChunk = 1;
        this.sandPerChunk2 = 3;
        this.clayPerChunk = 1;
        this.bigMushroomsPerChunk = 0;
        this.generateLakes = true;
        this.biome = par1BiomeGenBase;
        
        //this.treeGenerator = new WorldGenAtumTrees(true);
    }

    /**
     * Decorates the world. Calls code that was formerly (pre-1.8) in ChunkProviderGenerate.populate
     */
    @Override
    public void decorate(World par1World, Random par2Random, int par3, int par4)
    {
        if (this.currentWorld != null)
        {
            throw new RuntimeException("Already decorating!!");
        }
        else
        {
            this.currentWorld = par1World;
            this.randomGenerator = par2Random;
            this.chunk_X = par3;
            this.chunk_Z = par4;
            this.decorate();
            this.currentWorld = null;
            this.randomGenerator = null;
        }
    }

    /**
     * The method that does the work of actually decorating chunks
     */
    @Override
    protected void decorate()
    {
        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(currentWorld, randomGenerator, chunk_X, chunk_Z));
        
       this.generateOres();
        int i;
        int j;
        int k;

        long time;
        boolean doGen;
        
        /*boolean doGen = TerrainGen.decorate(currentWorld, randomGenerator, chunk_X, chunk_Z, SAND);
        for (i = 0; doGen && i < this.sandPerChunk2; ++i)
        {
            j = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            k = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            time = System.nanoTime();
            this.sandGen.generate(this.currentWorld, this.randomGenerator, j, this.currentWorld.getTopSolidOrLiquidBlock(j, k), k);
            //System.out.println("Gen Sand: " + (System.nanoTime() - time));
            //this.treeGenerator.generate(this.currentWorld, this.randomGenerator, j, this.currentWorld.getTopSolidOrLiquidBlock(j, k), k);
        }*/

        doGen = TerrainGen.decorate(currentWorld, randomGenerator, chunk_X, chunk_Z, SAND_PASS2);
        for (i = 0; doGen && i < this.sandPerChunk; ++i)
        {
            j = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            k = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            time = System.nanoTime();
            this.sandGen.generate(this.currentWorld, this.randomGenerator, j, this.currentWorld.getTopSolidOrLiquidBlock(j, k), k);
            //System.out.println("Gen More Sand: " + (System.nanoTime() - time));
        }

        i = this.treesPerChunk;

        if (this.randomGenerator.nextInt(10) == 0)
        {
            ++i;
        }

        int l;

        /*
        doGen = TerrainGen.decorate(currentWorld, randomGenerator, chunk_X, chunk_Z, TREE);
        i = (int) (this.randomGenerator.nextFloat() + 0.1);
        for (j = 0; doGen && j < i; ++j)
        {
            k = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            l = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            WorldGenerator worldgenerator = this.biome.getRandomWorldGenForTrees(this.randomGenerator);
            worldgenerator.setScale(1.0D, 1.0D, 1.0D);
            time = System.nanoTime();
            worldgenerator.generate(this.currentWorld, this.randomGenerator, k, this.currentWorld.getHeightValue(k, l), l);
            //System.out.println("Gen Trees: " + (System.nanoTime() - time));
        }*/

        int i1;

        time = System.nanoTime();
        doGen = TerrainGen.decorate(currentWorld, randomGenerator, chunk_X, chunk_Z, GRASS);
        for (j = 0; doGen && j < this.grassPerChunk; ++j)
        {
            k = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            l = this.randomGenerator.nextInt(128);
            i1 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            WorldGenerator worldgenerator1 = this.biome.getRandomWorldGenForGrass(this.randomGenerator);
            worldgenerator1.generate(this.currentWorld, this.randomGenerator, k, l, i1);
        }
        
        if(randomGenerator.nextFloat() < shrubChance)
        {
            k = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            l = this.randomGenerator.nextInt(128);
            i1 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            time = System.nanoTime();
            (new WorldGenShrub(AtumBlocks.shrub.blockID, 8)).generate(this.currentWorld, this.randomGenerator, k, l, i1);
            //System.out.println("Gen Shrub: " + (System.nanoTime() - time));
        }

        if(randomGenerator.nextFloat() < shrubChance)
        {
            k = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            l = this.randomGenerator.nextInt(128);
            i1 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            time = System.nanoTime();
            (new WorldGenShrub(AtumBlocks.weed.blockID, 8)).generate(this.currentWorld, this.randomGenerator, k, l, i1);
            //System.out.println("Gen Weeds: " + (System.nanoTime() - time));
        }

        doGen = TerrainGen.decorate(currentWorld, randomGenerator, chunk_X, chunk_Z, LAKE);
        if (doGen && this.generateLakes)
        {
            for (j = 0; j < 20; ++j)
            {
                k = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
                l = this.randomGenerator.nextInt(this.randomGenerator.nextInt(this.randomGenerator.nextInt(112) + 8) + 8);
                i1 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
                time = System.nanoTime();
                (new WorldGenLiquids(Block.lavaMoving.blockID)).generate(this.currentWorld, this.randomGenerator, k, l, i1);
                //System.out.println("Gen Lava: " + (System.nanoTime() - time));
            }
        }

        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(currentWorld, randomGenerator, chunk_X, chunk_Z));
    }

    /**
     * Standard ore generation helper. Generates most ores.
     */
    @Override
    protected void genStandardOre1(int par1, WorldGenerator par2WorldGenerator, int par3, int par4)
    {
        for (int l = 0; l < par1; ++l)
        {
            int i1 = this.chunk_X + this.randomGenerator.nextInt(16);
            int j1 = this.randomGenerator.nextInt(par4 - par3) + par3;
            int k1 = this.chunk_Z + this.randomGenerator.nextInt(16);
            par2WorldGenerator.generate(this.currentWorld, this.randomGenerator, i1, j1, k1);
        }
    }

    /**
     * Standard ore generation helper. Generates Lapis Lazuli.
     */
    @Override
    protected void genStandardOre2(int par1, WorldGenerator par2WorldGenerator, int par3, int par4)
    {
        for (int l = 0; l < par1; ++l)
        {
            int i1 = this.chunk_X + this.randomGenerator.nextInt(16);
            int j1 = this.randomGenerator.nextInt(par4) + this.randomGenerator.nextInt(par4) + (par3 - par4);
            int k1 = this.chunk_Z + this.randomGenerator.nextInt(16);
            par2WorldGenerator.generate(this.currentWorld, this.randomGenerator, i1, j1, k1);
        }
    }

    /**
     * Generates ores in the current chunk
     */
    @Override
    protected void generateOres()
    {
        MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Pre(currentWorld, randomGenerator, chunk_X, chunk_Z));
        if (TerrainGen.generateOre(currentWorld, randomGenerator, coalGen, chunk_X, chunk_Z, COAL))
        this.genStandardOre1(20, this.coalGen, 0, 128);
        if (TerrainGen.generateOre(currentWorld, randomGenerator, ironGen, chunk_X, chunk_Z, IRON))
        this.genStandardOre1(20, this.ironGen, 0, 64);
        if (TerrainGen.generateOre(currentWorld, randomGenerator, goldGen, chunk_X, chunk_Z, GOLD))
        this.genStandardOre1(2, this.goldGen, 0, 32);
        if (TerrainGen.generateOre(currentWorld, randomGenerator, redstoneGen, chunk_X, chunk_Z, REDSTONE))
        this.genStandardOre1(8, this.redstoneGen, 0, 16);
        if (TerrainGen.generateOre(currentWorld, randomGenerator, diamondGen, chunk_X, chunk_Z, DIAMOND))
        this.genStandardOre1(1, this.diamondGen, 0, 16);
        if (TerrainGen.generateOre(currentWorld, randomGenerator, lapisGen, chunk_X, chunk_Z, LAPIS))
        this.genStandardOre2(1, this.lapisGen, 16, 16);
        MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Post(currentWorld, randomGenerator, chunk_X, chunk_Z));
    }
}
