package net.ordahan.ormain.utils;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTagVisitor;
import net.minecraft.nbt.TagParser;

import static net.ordahan.ormain.ORMain.LOGGER;
public class Tags {

    public static String parseCompoundTag(CompoundTag tag) {
        return new StringTagVisitor().visit(tag);
    }
    public static CompoundTag parseString(String str) {
        try {
           return new TagParser(new StringReader(str)).readStruct();
        } catch (CommandSyntaxException e) {
            LOGGER.info("unable to read Tag '" + str + "': " + e.getMessage());
            return new CompoundTag();
        }
    }

}
