package com.whiz.app.boot.infrastructure.util;

import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class JasyptUtilTests {

    @Test
    void test01_decyptJwtSecret() {
        assertTrue("ZmQ0ZGI5NjQ0MDQwY2I4MjMxY2Y3ZmI3MjdhN2ZmMjNhODViOTg1ZGE0NTBjMGM4NDA5NzYxMjdjOWMwYWRmZTBlZjlhNGY3ZTg4Y2U3YTE1ODVkZDU5Y2Y3OGYwZWE1NzUzNWQ2YjFjZDc0NGMxZWU2MmQ3MjY1NzJmNTE0MzI="
            .equals(JasyptUtil.decyptPwd("EbfYkitulv73I2p0mXI50JMXoaxZTKJ7",
                "lh4qcb85ZQbscAGsQnJI+WTZ03xdwepGz3Hax3DRyz7jQPklQduHf+tbhNt8R6/Vf2qtEiPnpAM9h8OT39euU+XmR/yiZNhCg4uLXI4Ps+6iUHW7AfxdenAS0jBJiFFrIsx5IIaNLkq30JtKYm6NLGv1GuUdOSMvDu/BoqpDiZKFyrdTV8evrAe8wsxfDcnJAOwq8iXh0SVkMfKfvsoW0ktESEhN0ipOmQlmhtuK6vU8ClkLGmmuviyKWrUh1Dj/rZm18wXH26k=")));
    }

    @Test
    void test02_decyptOauth2Secret() {
        assertTrue("ed5b6147f00ec39109dcfaf5d34ef431f4fb53dc"
            .equals(JasyptUtil.decyptPwd("EbfYkitulv73I2p0mXI50JMXoaxZTKJ7",
                "CPCmU9PP4z2shsfpXkw21bl2LUuMWC1UwQdQKw5U8MRQwQQxA3nx+fgD061o0kispfHKoR1ahyfZAJ0Hk1PHvhhse5eQ6pZ2")));
    }

    @Test
    void test03_decyptMapAk() {
        assertTrue("YnGAe9P3U503u5S0SZhAGTjwCclv5xx8"
            .equals(JasyptUtil.decyptPwd("EbfYkitulv73I2p0mXI50JMXoaxZTKJ7",
                "ncm6YBAdNe4uGj4oxI/aMTCfoB0GeXEXUqcLr01pB4QWUt3e8yNg/9se+7rAKERxH4t0FF5qpdK8x/EvurxqiQ==")));
    }
}