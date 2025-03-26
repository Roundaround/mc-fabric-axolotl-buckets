plugins {
  id("roundalib-gradle") version "1.0.0"
}

roundalib {
  library {
    local = true
    version = "3.0.0"
  }
}

fabricApi {
  configureDataGeneration {
    client = true
  }
}
