/*
 *
 *  Copyright 2013 Netflix, Inc.
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 */
package com.netflix.zeno.testpojos;

import com.netflix.zeno.fastblob.record.FastBlobSchema;
import com.netflix.zeno.fastblob.record.FastBlobSchema.FieldType;
import com.netflix.zeno.serializer.NFDeserializationRecord;
import com.netflix.zeno.serializer.NFSerializationRecord;
import com.netflix.zeno.serializer.NFTypeSerializer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TypeDSerializer extends NFTypeSerializer<TypeD> {

    public TypeDSerializer() {
        super("TypeD");
    }

    @Override
    public void doSerialize(TypeD value, NFSerializationRecord rec) {
        serializePrimitive(rec, "val", value.getVal());
        serializeObject(rec, "a", "TypeA", value.getTypeA());
    }

    @Override
    protected TypeD doDeserialize(NFDeserializationRecord rec) {
        return new TypeD(
                deserializeInteger(rec, "val"),
                (TypeA) deserializeObject( rec, "TypeA", "a")
        );
    }

    @Override
    protected FastBlobSchema createSchema() {
        return schema(
                field("val", FieldType.INT),
                field("a")
        );
    }

    @Override
    public Collection<NFTypeSerializer<?>> requiredSubSerializers() {
        List<NFTypeSerializer<?>>serializers = new ArrayList<NFTypeSerializer<?>>();
        serializers.add(new TypeASerializer());
        return serializers;
    }

}