default:
	@echo "Please have a look at the Makefile or run 'make all'."

languages=python golang java cpp

build:
	@for l in $(languages); do \
		make build-$$l; \
	done

bench:
	make bench-solve
	make bench-model-only

bench-solve:
	@for l in $(languages); do \
		make bench-$$l; \
		make bench-$$l-1cpu; \
	done

bench-model-only:
	@for l in $(languages); do \
		make bench-model-$$l; \
		make bench-model-$$l-1cpu; \
	done

copy-hyperfine:
	@for l in $(languages); do \
		cp hyperfine ./$$l; \
	done

remove-hyperfine-copies:
	@for l in $(languages); do \
		rm ./$$l/hyperfine; \
	done

build-python:
	docker build -t gabesoich/palchecker:python ./python
build-golang:
	docker build -t gabesoich/palchecker:golang ./golang
build-java:
	docker build -t gabesoich/palchecker:java ./java
build-cpp:
	docker build -t gabesoich/palchecker:cpp ./cpp

bench-python:
	docker run --cpus 1 --cpuset-cpus 1 --rm -e "MAX_CHILDREN=9" -v $(CURDIR)/results:/var/results gabesoich/palchecker:python
bench-python-1cpu:
	echo "Nothing to do here"
bench-golang:
	docker run --rm -e "MAX_CHILDREN=13" -v $(CURDIR)/results:/var/results gabesoich/palchecker:golang
bench-golang-1cpu:
	docker run --cpus 1 --cpuset-cpus 1 -e "MAX_CHILDREN=11" -e "RESULTS_FILENAME=mc-golang-1cpu" --rm -v $(CURDIR)/results:/var/results gabesoich/palchecker:golang
bench-java:
	docker run --rm -e "MAX_CHILDREN=14" -v $(CURDIR)/results:/var/results gabesoich/palchecker:java
bench-java-1cpu:
	docker run --cpus 1 --cpuset-cpus 1 -e "MAX_CHILDREN=12" -e "RESULTS_FILENAME=mc-java-1cpu" --rm -v $(CURDIR)/results:/var/results gabesoich/palchecker:java
bench-cpp:
	docker run --rm -e "MAX_CHILDREN=14" -v $(CURDIR)/results:/var/results gabesoich/palchecker:cpp
bench-cpp-1cpu:
	docker run --cpus 1 --cpuset-cpus 1 -e "MAX_CHILDREN=12" -e "RESULTS_FILENAME=mc-cpp-1cpu" --rm -v $(CURDIR)/results:/var/results gabesoich/palchecker:cpp

bench-model-python:
	docker run --cpus 1 --cpuset-cpus 1 --rm -e "MAX_CHILDREN=9" -e "STOP_AFTER_MODEL_GENERATED=1" -e "RESULTS_FILENAME=mc-model-only-python" -v $(CURDIR)/results:/var/results gabesoich/palchecker:python
bench-model-python-1cpu:
	echo "Nothing to do here"
bench-model-golang:
	docker run --rm -e "MAX_CHILDREN=13" -e "STOP_AFTER_MODEL_GENERATED=1" -e "RESULTS_FILENAME=mc-model-only-golang" -v $(CURDIR)/results:/var/results gabesoich/palchecker:golang
bench-model-golang-1cpu:
	docker run --cpus 1 --cpuset-cpus 1 -e "MAX_CHILDREN=11" -e "STOP_AFTER_MODEL_GENERATED=1" -e "RESULTS_FILENAME=mc-model-only-golang-1cpu" --rm -v $(CURDIR)/results:/var/results gabesoich/palchecker:golang
bench-model-java:
	docker run --rm -e "MAX_CHILDREN=14" -e "STOP_AFTER_MODEL_GENERATED=1" -e "RESULTS_FILENAME=mc-model-only-java" -v $(CURDIR)/results:/var/results gabesoich/palchecker:java
bench-model-java-1cpu:
	docker run --cpus 1 --cpuset-cpus 1 -e "MAX_CHILDREN=12" -e "STOP_AFTER_MODEL_GENERATED=1" -e "RESULTS_FILENAME=mc-model-only-java-1cpu" --rm -v $(CURDIR)/results:/var/results gabesoich/palchecker:java
bench-model-cpp:
	docker run --rm -e "MAX_CHILDREN=14" -e "STOP_AFTER_MODEL_GENERATED=1" -e "RESULTS_FILENAME=mc-model-only-cpp" -v $(CURDIR)/results:/var/results gabesoich/palchecker:cpp
bench-model-cpp-1cpu:
	docker run --cpus 1 --cpuset-cpus 1 -e "MAX_CHILDREN=12" -e "STOP_AFTER_MODEL_GENERATED=1" -e "RESULTS_FILENAME=mc-model-only-cpp-1cpu" --rm -v $(CURDIR)/results:/var/results gabesoich/palchecker:cpp
