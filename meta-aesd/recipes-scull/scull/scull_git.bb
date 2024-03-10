LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://../LICENSE;md5=f098732a73b5f6f3430472f5b094ffdb"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-qrogers6.git;protocol=ssh;branch=master"
SRC_URI += "file://scull_init"

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "0105de64b06bf6535fb2cc5e37df546f7186d196"

S = "${WORKDIR}/git/scull"

inherit module

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/scull"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "scull_init"
INITSCRIPT_PARAMS = "defaults 90 10"

FILES:${PN} += "${sysconfdir}/*"

do_configure () {
  :
}

do_compile () {
  oe_runmake
}

do_install () {
  install -d ${D}${sysconfdir}/init.d
  install -m 0755 ${WORKDIR}/scull_init ${D}${sysconfdir}/init.d
  install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/
  install -m 0755 ${S}/scull.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/
}
