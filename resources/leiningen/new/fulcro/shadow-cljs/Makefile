test:
	npm install
	npx shadow-cljs compile ci-tests
	npx karma start --single-run
	lein do clean, test-refresh :run-once # clean is needed in case AOT stuff is around

i18n-extract:
	@echo "Building Clojurescript"
	@shadow-cljs release i18n
	@echo "Running extraction"
	@xgettext --from-code=UTF-8 --debug -k -ktr:1 -ktrc:1c,2 -ktrf:1 -o resources/i18n/messages.pot target/i18n/i18n.js
	@echo "Done."
	@echo 
	@echo "Use msgmerge to merge the new messages.pot with existing translations."
	@echo 
	@echo "  msgmerge --force-po --no-wrap -U es.po messages.pot"
	@echo 
	@echo "If you have a new locale you'd like to support, generate a new PO file for it using:"
	@echo 
	@echo "   msginit --no-translator -l LOCALE --no-wrap -o resources/i18n/LOCALE.po -i resources/i18n/messages.pot"
	@echo 
	@echo "Now give all of your PO files in i18n to a translator (or use PoEdit Pro to get close). Then put the"
	@echo "updated PO files back in i18n and run 'make i18n-generate"
	@echo 
	@echo "See the GNU Gettext manual for more instructions on working with POT and PO files."

.PHONY: i18n-extract test
