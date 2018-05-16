General informations and why we use this format:
===

The original NBT based Anvil format generates much data which needs to be parsed. Also the data is designed to be read in as arrays
or nibbles. To get it into the correct form you need to read the full NBT Tree and resort / arrange the data that a PE client sub-
chunk is properly formatted. This has huge loading times currently and we don't see any optimizations in our implementation, 
maybe we will find some in the future but currently we think its better to get our own format going which is meant to be read fast
and updated often (for survival build worlds for example).

The new gomint format will provide regions of 128x128 chunks to be saved in folders. This gives folder 0/0 for chunk x:16 / z:116.
So data for that chunk would be saved to ./0/0/16_116.chunkdata. The resulting structure allows for loading multiple chunks at once,
this is currently not possible (with the anvil format) due to the fact that chunks are merged together into a region file and reading 
those multiple times is inefficient since there is no true form of I/O caching from the OS.

To further improve read performance all data files in this format are divided into sections. Each section has a integer at the beginning
defining how long the following section will be. This allows either skipping sections or simply adding more whilst supporting old
versions of the same format if needed. To provide forward compatibility each file has a empty (0 length) section at the end.

Lets talk some data formats here. We will just use O(1) data structures to write data. This means we don't use varints, varlongs or other
tricks to shrink data size (we only use lz4 compression). Just raw integer, float, double, byte allowed :)

Now to the writing side of the format. We use commit logs to directly save changes to the chunks. This affects every data change, regardless
of the data changing like block breaks / block places, entity movements, inventory changes etc. All data collected has to be appended, the file will
be merged into the chunk data. Commit logs will be merged on autosave interval or will never be written when autosave is disabled.
Those commit logs will have no sections (and are the only exception from that rule) since they append data prefixed with their own length
(one could argue its section based, but the last section is missing).

Data definitions:
===

string: short of the byte length + utf8 bytes


Current format plans:
===

Index file (Location ./world.index):
---
There is a "index" file at the root of the world folder. This file holds basic information about the world like name, generator options, spawn location etc.

Format of the index:

```
Section 1 (World metadata):
  section length (int)
  levelname (string)
  spawn x (int)
  spawn y (int)
  spawn z (int)
  amount of key:value pairs (int)
    key (string)
	value (string)
```

Chunk data (Location ./x/z/cX_cZ.chunkdata):
---

```
Section 1 (Overworld Blocks):
  section length (int)
  4096 times:
    block id (int)
    data (short)
    nbt data length (int)
      nbt data
Section 2 (Underwater Blocks):
  section length (int)
  4096 times:
    block id (int)
    data (short)
    nbt data length (int)
      nbt data
```

Entity data (Location ./x/z/cX_cZ.entitydata):
---

```
Section 1-n (Entity NBT):
  section/nbt length
  nbt data
```
