package com.java_app.demo.location;

import com.java_app.demo.location.dto.KeyDto;

public interface LocationService {

  KeyDto getUserLocationByIp(String ip);
}
