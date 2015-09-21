/*
 *      Copyright (C) 2015  higherfrequencytrading.com
 *
 *      This program is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License.
 *
 *      This program is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.openhft.chronicle.map;

import net.openhft.lang.io.Bytes;
import net.openhft.lang.io.serialization.BytesMarshaller;
import org.junit.Test;

public class Issue43Test {

    public static void main(String[] args) {
        new Issue43Test().testIssue43();
    }

    @Test
    public void testIssue43() {
        try {
            ChronicleMap<Long, ValueWrapper> map = ChronicleMapBuilder
                    .of(Long.class, ValueWrapper.class)
                    .entries(512)
                    .valueMarshaller(new ArrayMarshaller())
                    .constantValueSizeBySample(new ValueWrapper(new double[128]))
                    .createPersistedTo(Builder.getPersistenceFile());
            System.out.println("Created the monkey map ValueWrapper 128");
        } catch (Throwable ex) {
            System.out.println(ex);
        }
    }


    private static class ValueWrapper {
        private final double values[];

        public ValueWrapper(double[] values) {
            this.values = values;
        }
    }

    private static class ArrayMarshaller implements BytesMarshaller<ValueWrapper> {

        @Override
        public void write(Bytes bytes, ValueWrapper vw) {
            bytes.writeInt(vw.values.length);

            for (int i = 0; i < vw.values.length; i ++) {
                bytes.writeDouble(vw.values[i]);
            }
        }

        @Override
        public ValueWrapper read(Bytes bytes) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public ValueWrapper read(Bytes arg0, ValueWrapper arg1) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }
}
