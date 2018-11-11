test:
	npm install
	npx shadow-cljs compile ci-tests
	npx karma start --single-run
	lein do clean, test-refresh :run-once # clean is needed in case AOT stuff is around

.PHONY: test
