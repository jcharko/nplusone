package com.example.nplusone.nplusone;

import com.example.nplusone.nplusone.sandbox.DataFetchSandbox;
import com.example.nplusone.nplusone.sandbox.DataInitializer;
import com.example.nplusone.nplusone.sandbox.model.A;
import com.example.nplusone.nplusone.sandbox.model.B;
import net.ttddyy.dsproxy.asserts.ProxyTestDataSource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.annotation.DirtiesContext;

import javax.sql.DataSource;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(NplusoneApplicationTests.DataSourceConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class NplusoneApplicationTests {

	@Autowired
	private DataInitializer dataInitializer;

	@Autowired
	private DataFetchSandbox dataFetchSandbox;

	@Autowired
	private ProxyTestDataSource proxyDataSource;

	@Test
	void n_plus_one_problem() {
		List<A> created = dataInitializer.prepareData(5);

		// reset ds query executions (inserts)
		proxyDataSource.reset();

		List<A> fetched = dataFetchSandbox.fetchDataNPlusOneProblem();

		assertThat(proxyDataSource.getQueryExecutions().size()).isEqualTo(259);
		assertThat(created.size()).isEqualTo(fetched.size());
		assertThat(sizeOfB(created)).isEqualTo(sizeOfB(fetched));
		assertThat(sizeOfC(created)).isEqualTo(sizeOfC(fetched));
		assertThat(sizeOfD(created)).isEqualTo(sizeOfD(fetched));
	}

	@Test
	void resolved_n_plus_one_problem() {

		List<A> created = dataInitializer.prepareData(5);

		// reset ds query executions (inserts)
		proxyDataSource.reset();

		List<A> fetched = dataFetchSandbox.fetchDataWithoutNPlusOneProblem();

		assertThat(proxyDataSource.getQueryExecutions().size()).isEqualTo(3);
		assertThat(created.size()).isEqualTo(fetched.size());
		assertThat(sizeOfB(created)).isEqualTo(sizeOfB(fetched));
		assertThat(sizeOfC(created)).isEqualTo(sizeOfC(fetched));
		assertThat(sizeOfD(created)).isEqualTo(sizeOfD(fetched));
	}

	private long sizeOfB(List<A> created) {
		return created.stream()
				.flatMap(a -> a.getListOfB().stream())
				.count();
	}

	private long sizeOfC(List<A> created) {
		return created.stream()
				.flatMap(a -> a.getListOfB().stream())
				.flatMap(b -> b.getListOfC().stream())
				.count();
	}

	private long sizeOfD(List<A> created) {
		return created.stream()
				.flatMap(a -> a.getListOfB().stream())
				.flatMap(b -> b.getListOfC().stream())
				.flatMap(c -> c.getListOfD().stream())
				.count();
	}

	@TestConfiguration
	public static class DataSourceConfiguration {

		@Bean
		public DataSource dataSource() {
			EmbeddedDatabaseBuilder databaseBuilder = new EmbeddedDatabaseBuilder();
			return new ProxyTestDataSource(databaseBuilder.setType(EmbeddedDatabaseType.H2).build());
		}
	}
}
