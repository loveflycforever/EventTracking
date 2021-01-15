import com.apoem.mmxx.eventtracking.BeanCopierUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.core.Converter;
import org.junit.Test;

/**
 * 基于ASM的cglib BeanCopier拷贝速度基本和手写get/set方法的速度无异
 */
public class BeanCopierUtilsTest {

    @Data
    private static class Base {
        private String serialNumber;
    }

    @Data
    @ToString(callSuper = true)
    @EqualsAndHashCode(callSuper = true)
    private static class RequestDto extends Base {
        private String timestamp;
    }

    @Data
    @ToString(callSuper = true)
    @EqualsAndHashCode(callSuper = true)
    private static class Cmd  extends Base {
        private String timestamp;
    }

    @Test
    public void testCopy() {
        RequestDto appAcquisitionRequestDto = new RequestDto();
        appAcquisitionRequestDto.setSerialNumber("www");

        Cmd copy = BeanCopierUtils.copy(appAcquisitionRequestDto, Cmd.class);

        System.out.println(copy.getSerialNumber());
    }

    @Test
    public void testCopyPerf() {
        RequestDto dto = new RequestDto();
        dto.setSerialNumber("www");

//        int loop = 1;
        int loop = 100;


        long start = System.currentTimeMillis();

        // 每次新建 BeanCopier loop-1/85 loop-1百万/103
        for (int i = 0; i < loop; i++) {
            Cmd copy = BeanCopierUtils.copy(dto, Cmd.class);
            System.out.println(copy.getSerialNumber());
        }


        // 缓存 BeanCopier loop-1/95 loop-1百万/95
//        for (int i = 0; i < loop; i++) {
//            AppAcquisitionCmd targetObject = new AppAcquisitionCmd();
//            BEAN_COPIER.copy(dto, targetObject, null);
////            log.info(targetObject.getSerialNumber());
//        }

        long finish = System.currentTimeMillis();

        System.out.println(finish + "");
        System.out.println(start + "");
        System.out.println(finish - start + "");
    }

    /**
     * Cglib 深拷贝转换器
     */
    class McConverter implements Converter {

        @Override
        public Object convert(Object value, Class target, Object context) {
            if(target.isSynthetic()){
                BeanCopier.create(target, target, true).copy(value, value, this);
            }
            return value;
        }
    }

    @Test
    public void testMultiCopy() {

        RequestDto dto = new RequestDto();
        dto.setSerialNumber("www");

        RequestDto dto2 = new RequestDto();
        dto2.setTimestamp("eee");

        Cmd targetObject = new Cmd();

        BeanCopierUtils.copy(dto, targetObject);

        System.out.println(targetObject);

        BeanCopierUtils.copy(dto2, targetObject);

//        BeanCopier beanCopier = BeanCopier.create(AppAcquisitionRequestDto.class, AppAcquisitionCmd.class, true);
//        beanCopier.copy(dto, targetObject, new McConverter());

        System.out.println(targetObject);
    }
}