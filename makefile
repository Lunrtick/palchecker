default:
	@echo "Please have a look at the Makefile or run 'make all'."

languages=python golang java cpp

copy-hyperfine:
	@for l in $(languages); do \
		cp hyperfine ./$$l; \
	done

remove-hyperfine-copies:
	@for l in $(languages); do \
		rm ./$$l/hyperfine; \
	done

build:
	make copy-hyperfine
	@for l in $(languages); do \
		make build-$$l; \
	done
	make remove-hyperfine-copies


build-python:
	docker build -t gabesoich/palchecker:python ./python
build-golang:
	docker build -t gabesoich/palchecker:golang ./golang
build-java:
	docker build -t gabesoich/palchecker:java ./java
build-cpp:
	docker build -t gabesoich/palchecker:cpp ./cpp

bench:
	@for l in $(languages); do \
		make bench-$$l; \
	done

bench-python:
	docker run --rm -v $(CURDIR)/results:/var/results gabesoich/palchecker:python
bench-golang:
	docker run --rm -v $(CURDIR)/results:/var/results gabesoich/palchecker:golang
bench-java:
	docker run --rm -v $(CURDIR)/results:/var/results gabesoich/palchecker:java
bench-cpp:
	docker run --rm -v $(CURDIR)/results:/var/results gabesoich/palchecker:cpp