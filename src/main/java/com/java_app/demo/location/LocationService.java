package com.java_app.demo.location;

import com.java_app.demo.location.dto.LocationDto;

public interface LocationService {

  LocationDto getUserLocationByIp(String ip);
}
