test:
	npm install
	lein do clean, doo chrome automated-tests once
	lein test-refresh :run-once

i18n-extract:
	@rm -rf target/
	@echo "Building Clojurescript"
	@lein cljsbuild once i18n
	@echo "Running extraction"
	@xgettext --from-code=UTF-8 --debug -k -ktr_alpha:1 -ktrc_alpha:1c,2 -ktrf_alpha:1 -o resources/i18n/messages.pot target/i18n.js
	@echo "Done."
	@echo "Prior translations were merged in all of your exising locales (unless you saw errors)."
	@echo 
	@echo "If you have a new locale you'd like to support, generate a new PO file for it using:"
	@echo 
	@echo "msginit --no-translator -l LOCALE --no-wrap -o i18n/LOCALE.po -i i18n/messages.pot"
	@echo 
	@echo "Now give all of your PO files in i18n to a translator (or use PoEdit Pro to get close). Then put the"
	@echo "updated PO files back in i18n and run 'make i18n-generate"
	@echo 
	@echo "See the GNU Gettext manual for more instructions on working with POT and PO files."

.PHONY: i18n-extract test
