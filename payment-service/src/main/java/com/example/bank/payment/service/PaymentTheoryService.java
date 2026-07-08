package com.example.bank.payment.service;

import com.example.bank.payment.dto.TheoryAnswer;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Architecture/Tech: Domain knowledge service exposing theoretical Java/design interview answers.
 */
@Service
public class PaymentTheoryService {

    public List<TheoryAnswer> answers() {
        return List.of(
                new TheoryAnswer(
                        "What is Dependency Injection, and what are its primary use cases?",
                        "Dependency Injection supplies collaborators from outside a class instead of creating them internally. It enables loose coupling, easier testing with mocks/stubs, and runtime composition via frameworks like Spring."
                ),
                new TheoryAnswer(
                        "Which design principle(s) would you apply to make a class more testable?",
                        "Use SOLID: especially Single Responsibility, Dependency Inversion, and Interface Segregation. Keep side effects at boundaries and move pure business logic into small deterministic methods."
                ),
                new TheoryAnswer(
                        "Given an existing class, what design changes would improve maintainability and testability?",
                        "Split mixed responsibilities, inject external dependencies, replace static/global state, add clear interfaces, and isolate branching behavior into strategy classes."
                ),
                new TheoryAnswer(
                        "If a class contains multiple if-else conditions based on different types, which pattern would you use?",
                        "Use Strategy (or polymorphism) so each type-specific behavior lives in its own class. A registry/map selects the strategy by type and removes long conditional chains."
                ),
                new TheoryAnswer(
                        "How are equals() and hashCode() related in Java? What issues can arise if one is missing?",
                        "Equal objects must return the same hashCode. If equals is overridden without hashCode, hash-based collections (HashMap/HashSet) can lose entries or fail lookups."
                ),
                new TheoryAnswer(
                        "Payment transaction scenario: design considerations and implementation",
                        "Use a layered design: controller for transport, service for orchestration, strategy for fee rules, and dedicated validator/ledger for debug workflow. Keep domain immutable, add centralized exception mapping, and expose deterministic methods for analytics."
                ),
                new TheoryAnswer(
                        "How objects get promoted to different heap generations?",
                        "Objects are first allocated in the young generation (Eden), then surviving GC cycles move to Survivor spaces and eventually to old generation based on age and collector policy."
                ),
                new TheoryAnswer(
                        "Exceptions in Java",
                        "Checked exceptions are enforced by the compiler; unchecked exceptions (RuntimeException) are programming/data issues. Use specific exceptions, preserve causes, and avoid swallowing errors."
                )
        );
    }
}
