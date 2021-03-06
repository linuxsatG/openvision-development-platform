MAINTAINER = "team@sabnzbd.org"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYRIGHT.txt;md5=6c2cd2089133de5067e13a6d4f75afef"

DEPENDS = "${PYTHONNAMEONLY}"
RDEPENDS_${PN} = "\
	${PYTHONNAMEONLY}-core ${PYTHONNAMEONLY}-shell ${PYTHONNAMEONLY}-compression ${PYTHONNAMEONLY}-crypt ${PYTHONNAMEONLY}-ctypes \
	${PYTHONNAMEONLY}-cheetah ${PYTHONNAMEONLY}-misc ${@bb.utils.contains("PYTHONNAMEONLY", "python3", "", "python-subprocess", d)} ${PYTHONNAMEONLY}-html ${PYTHONNAMEONLY}-email ${PYTHONNAMEONLY}-yenc \
	"
RRECOMMENDS_${PN} = "par2cmdline unrar"

SRC_URI = "https://github.com/sabnzbd/sabnzbd/archive/${PV}.tar.gz \
	file://sabnzbd \
	file://sabnzbd.conf \
	file://init-functions \
	"

SRC_URI[md5sum] = "ccee8c716f24d1a6f986844055c94cd0"
SRC_URI[sha256sum] = "33999e3ed15c08bb36d58a07e4e936a2e17b2656f10e3b6ae4716e627ba15a39"

S = "${WORKDIR}/sabnzbd-${PV}"

INSTALLDIR = "${libdir}/${PN}"

PACKAGES = "${PN}-doc ${PN}-src ${PN}"

FILES_${PN}-src = "${INSTALLDIR}/*/*.py ${INSTALLDIR}/*/*/*.py"
RDEPENDS_${PN}-src = "python"
FILES_${PN}-doc = "${INSTALLDIR}/*.txt ${INSTALLDIR}/licenses ${INSTALLDIR}/interfaces/*/licenses"
FILES_${PN} = "${INSTALLDIR} ${sysconfdir}/init.d/sabnzbd ${sysconfdir}/init.d/init-functions ${sysconfdir}/enigma2/sabnzbd.conf"

inherit update-rc.d compile_python_pyo
INITSCRIPT_NAME = "sabnzbd"
INITSCRIPT_PARAMS = "defaults"

do_install() {
	install -d ${D}${INSTALLDIR}
	cp -r . ${D}${INSTALLDIR}/
	install -d ${D}${sysconfdir}/init.d
	install -m 755 ${WORKDIR}/sabnzbd ${D}${sysconfdir}/init.d/sabnzbd
	install -m 755 ${WORKDIR}/init-functions ${D}${sysconfdir}/init.d/init-functions
	install -d ${D}${sysconfdir}/enigma2
	install -m 644 ${WORKDIR}/sabnzbd.conf ${D}${sysconfdir}/enigma2/sabnzbd.conf
}
