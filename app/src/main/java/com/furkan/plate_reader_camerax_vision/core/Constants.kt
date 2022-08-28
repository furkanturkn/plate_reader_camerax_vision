package com.furkan.plate_reader_camerax_vision.core


object Constants {

    const val EMPTY_IMAGE_URI = "file://dev/null"

    //99 X 9999
    const val PLATE_REGEX_TEMPLATE_1 = """\b\d{2}.{0,1}[^\d\W]{0,1}.{0,1}\b\d{4,5}\b"""
    // #99 XX 999
    const val PLATE_REGEX_TEMPLATE_2 = """\b\d{2}.{0,1}[^\d\W]{0,1}.{0,1}\b[^\d\W]{0,1}.{0,1}\b\d{3,4}\b"""
    // #99 XXX 99
    const val PLATE_REGEX_TEMPLATE_3 = """\b\d{2}.{0,1}[^\d\W]{0,1}.{0,1}\b[^\d\W]{0,1}.{0,1}\w{0,1}.{0,1}\b\d{2,3}\b"""

    const val RECTANGLE_PATH = "M 10 10 H 300 V 65 H 10 L 10 10"
}